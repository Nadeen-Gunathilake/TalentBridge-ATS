package com.noobdevs.talentbridge_ats.service;

import com.noobdevs.talentbridge_ats.dto.ApplicationRequestDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationService {
    List<ApplicationResponseDTO> getAllApplications();
    ApplicationResponseDTO getApplicationById(Long id);
    ApplicationResponseDTO createApplication(ApplicationRequestDTO dto);
    ApplicationResponseDTO changeStatus(Long id, ApplicationStatus status);
    void deleteApplication(Long id);
}
