package com.noobdevs.talentbridge_ats.service.impl;

import com.noobdevs.talentbridge_ats.dto.RecruiterRequestDTO;
import com.noobdevs.talentbridge_ats.dto.RecruiterResponseDTO;
import com.noobdevs.talentbridge_ats.exception.ResourceNotFoundException;
import com.noobdevs.talentbridge_ats.mapper.RecruiterMapper;
import com.noobdevs.talentbridge_ats.models.Job;
import com.noobdevs.talentbridge_ats.models.Recruiter;
import com.noobdevs.talentbridge_ats.repository.JobRepository;
import com.noobdevs.talentbridge_ats.repository.RecruiterRepository;
import com.noobdevs.talentbridge_ats.service.RecruiterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterMapper recruiterMapper;
    private final RecruiterRepository recruiterRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    public RecruiterServiceImpl(RecruiterMapper recruiterMapper,
                                RecruiterRepository recruiterRepository,
                                JobRepository jobRepository,
                                PasswordEncoder passwordEncoder) {
        this.recruiterMapper = recruiterMapper;
        this.recruiterRepository = recruiterRepository;
        this.jobRepository = jobRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RecruiterResponseDTO getRecruiterById(Long id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + id));
        return recruiterMapper.toResponseDTO(recruiter);
    }

    @Override
    public RecruiterResponseDTO createRecruiter(RecruiterRequestDTO dto) {
        Recruiter recruiter = recruiterMapper.toEntity(dto);
        recruiter.setPassword(passwordEncoder.encode(dto.getPassword()));
        return recruiterMapper.toResponseDTO(recruiterRepository.save(recruiter));
    }

    @Override
    public RecruiterResponseDTO updateRecruiter(Long id, RecruiterRequestDTO dto) {
        Recruiter existing = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + id));
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPassword(passwordEncoder.encode(dto.getPassword())); // was storing raw password before
        existing.setRecruiterType(dto.getRecruiterType());

        return recruiterMapper.toResponseDTO(recruiterRepository.save(existing));
    }

    @Override
    public void deleteRecruiter(Long id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + id));

        List<Job> jobs = jobRepository.findByCreatedById(id);
        for (Job job : jobs) {
            job.setCreatedBy(null);
        }
        jobRepository.saveAll(jobs);

        recruiterRepository.deleteById(id);
    }

}
