package com.noobdevs.talentbridge_ats.service.impl;

import com.noobdevs.talentbridge_ats.dto.ApplicationRequestDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.dto.JobRequestDTO;
import com.noobdevs.talentbridge_ats.dto.JobResponseDTO;
import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import com.noobdevs.talentbridge_ats.enums.JobStatus;
import com.noobdevs.talentbridge_ats.mapper.ApplicationMapper;
import com.noobdevs.talentbridge_ats.mapper.JobMapper;
import com.noobdevs.talentbridge_ats.models.Application;
import com.noobdevs.talentbridge_ats.models.Job;
import com.noobdevs.talentbridge_ats.repository.ApplicationRepository;
import com.noobdevs.talentbridge_ats.repository.JobRepository;
import com.noobdevs.talentbridge_ats.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public List<ApplicationResponseDTO> getAllApplications(){
        return applicationRepository.findAll().stream()
                .map(applicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponseDTO getApplicationById(Long id){
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
        return applicationMapper.toResponseDTO(application);

    }

    @Override
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO dto){
        Application application=applicationMapper.toEntity(dto);
        return applicationMapper.toResponseDTO(applicationRepository.save(application));
    }

    @Override
    public void deleteApplication(Long id){
        applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
        applicationRepository.deleteById(id);
    }

    @Override
    public ApplicationResponseDTO changeStatus(Long id, ApplicationStatus status){
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
        application.setStatus(status);
        return applicationMapper.toResponseDTO(applicationRepository.save(application));
    }
}
