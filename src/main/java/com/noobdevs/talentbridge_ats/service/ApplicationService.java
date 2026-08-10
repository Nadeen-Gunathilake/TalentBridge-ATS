package com.noobdevs.talentbridge_ats.service;

import com.noobdevs.talentbridge_ats.dto.ApplicationNoteResponseDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationRecruiterViewDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationService {

    List<ApplicationResponseDTO> getMyApplications(String candidateEmail);
    ApplicationResponseDTO getMyApplicationById(Long id, String candidateEmail);
    ApplicationResponseDTO applyToJob(Long jobId, String coverNote, MultipartFile resume, String candidateEmail);
    ApplicationResponseDTO withdrawApplication(Long id, String candidateEmail);
    Page<ApplicationRecruiterViewDTO> getApplicationsForJob(Long jobId, ApplicationStatus status, Pageable pageable);
    ApplicationRecruiterViewDTO getApplicationByIdForRecruiter(Long id);
    ApplicationRecruiterViewDTO changeStatus(Long id, ApplicationStatus status);
    ApplicationRecruiterViewDTO rateApplication(Long id, Integer rating);
    ApplicationNoteResponseDTO addNote(Long applicationId, String content, String recruiterEmail);
    List<ApplicationNoteResponseDTO> getNotes(Long applicationId);
    String getResumePath(Long id);
}
