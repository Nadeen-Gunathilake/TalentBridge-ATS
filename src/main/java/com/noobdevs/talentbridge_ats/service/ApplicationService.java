package com.noobdevs.talentbridge_ats.service;

import com.noobdevs.talentbridge_ats.dto.ApplicationRecruiterViewDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationService {

    List<ApplicationResponseDTO> getMyApplications(String candidateEmail);
    ApplicationResponseDTO getMyApplicationById(Long id, String candidateEmail);
    ApplicationResponseDTO applyToJob(Long jobId, String coverNote, MultipartFile resume, String candidateEmail);
    ApplicationResponseDTO withdrawApplication(Long id, String candidateEmail);
    List<ApplicationRecruiterViewDTO> getApplicationsForJob(Long jobId);
    ApplicationRecruiterViewDTO getApplicationByIdForRecruiter(Long id);
    ApplicationRecruiterViewDTO changeStatus(Long id, ApplicationStatus status);
    String getResumePath(Long id);
}
