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
import com.noobdevs.talentbridge_ats.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<JobResponseDTO> getAllJobs(boolean isRecruiter) {
        List<Job> jobs = isRecruiter
                ? jobRepository.findAll()
                : jobRepository.findByStatus(JobStatus.OPEN);
        return jobs.stream().map(jobMapper::toResponseDTO).collect(Collectors.toList());
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
        existing.setStatus(dto.getStatus());

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
        job.setStatus(status);
        return jobMapper.toResponseDTO(jobRepository.save(job));
    }
}
