package com.noobdevs.talentbridge_ats.service.impl;

import com.noobdevs.talentbridge_ats.dto.JobRequestDTO;
import com.noobdevs.talentbridge_ats.dto.JobResponseDTO;
import com.noobdevs.talentbridge_ats.enums.JobStatus;
import com.noobdevs.talentbridge_ats.exception.ResourceNotFoundException;
import com.noobdevs.talentbridge_ats.mapper.JobMapper;
import com.noobdevs.talentbridge_ats.models.Job;
import com.noobdevs.talentbridge_ats.models.Recruiter;
import com.noobdevs.talentbridge_ats.repository.JobRepository;
import com.noobdevs.talentbridge_ats.repository.RecruiterRepository;
import com.noobdevs.talentbridge_ats.repository.spec.JobSpecifications;
import com.noobdevs.talentbridge_ats.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final RecruiterRepository recruiterRepository;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, RecruiterRepository recruiterRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.recruiterRepository = recruiterRepository;
    }

    @Override
    public Page<JobResponseDTO> getAllJobs(boolean isRecruiter, JobStatus status, String workMode,
                                           String employmentType, String location, String keyword, Pageable pageable) {

        JobStatus effectiveStatus = isRecruiter ? status : JobStatus.OPEN;

        List<Specification<Job>> specs = new ArrayList<>();
        if (effectiveStatus != null) {
            specs.add(JobSpecifications.hasStatus(effectiveStatus));
        }
        if (workMode != null && !workMode.isBlank()) {
            specs.add(JobSpecifications.hasWorkMode(workMode));
        }
        if (employmentType != null && !employmentType.isBlank()) {
            specs.add(JobSpecifications.hasEmploymentType(employmentType));
        }
        if (location != null && !location.isBlank()) {
            specs.add(JobSpecifications.hasLocation(location));
        }
        if (keyword != null && !keyword.isBlank()) {
            specs.add(JobSpecifications.titleContains(keyword));
        }

        Specification<Job> spec = Specification.allOf(specs);

        return jobRepository.findAll(spec, pageable).map(jobMapper::toResponseDTO);
    }

    @Override
    public JobResponseDTO getJobById(Long id, boolean isRecruiter) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        if (!isRecruiter && job.getStatus() != JobStatus.OPEN) {
            throw new ResourceNotFoundException("Job not found with id: " + id);
        }

        return jobMapper.toResponseDTO(job);
    }

    @Override
    public JobResponseDTO createJob(JobRequestDTO dto, String recruiterEmail) {
        Recruiter recruiter = recruiterRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));

        Job job = jobMapper.toEntity(dto);
        job.setCreatedBy(recruiter);
        return jobMapper.toResponseDTO(jobRepository.save(job));
    }

    @Override
    public JobResponseDTO updateJob(Long id, JobRequestDTO dto) {
        Job existing = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setLocation(dto.getLocation());
        existing.setWork_mode(dto.getWork_mode());
        existing.setEmployment_type(dto.getEmployment_type());
        existing.setRequired_skills(dto.getRequired_skills());
        existing.setSalary_range(dto.getSalary_range());
        existing.setClosing_date(dto.getClosing_date());

        return jobMapper.toResponseDTO(jobRepository.save(existing));
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        jobRepository.deleteById(id);
    }

    @Override
    public JobResponseDTO changeStatus(Long id, JobStatus status) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        validateJobStatusTransition(job.getStatus(), status);

        job.setStatus(status);
        return jobMapper.toResponseDTO(jobRepository.save(job));
    }

    private void validateJobStatusTransition(JobStatus from, JobStatus to) {
        if (from == to) {
            return;
        }
        boolean legal = (from == JobStatus.DRAFT && to == JobStatus.OPEN)
                || (from == JobStatus.OPEN && to == JobStatus.CLOSED);
        if (!legal) {
            throw new IllegalStateException("Cannot move a job from " + from + " to " + to);
        }
    }
}
