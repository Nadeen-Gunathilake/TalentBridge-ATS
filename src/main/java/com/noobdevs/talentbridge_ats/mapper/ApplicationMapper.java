package com.noobdevs.talentbridge_ats.mapper;

import com.noobdevs.talentbridge_ats.dto.*;
import com.noobdevs.talentbridge_ats.models.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationResponseDTO toResponseDTO(Application entity) {
        ApplicationResponseDTO dto = new ApplicationResponseDTO();
        dto.setId(entity.getId());
        dto.setJobId(entity.getJob().getId());
        dto.setJobTitle(entity.getJob().getTitle());
        dto.setStatus(entity.getStatus());
        dto.setCoverNote(entity.getCoverNote());
        dto.setAppliedAt(entity.getAppliedAt());
        return dto;
    }

    // Recruiter-facing view — full detail including rating (FR-R7/R8)
    public ApplicationRecruiterViewDTO toRecruiterViewDTO(Application entity) {
        ApplicationRecruiterViewDTO dto = new ApplicationRecruiterViewDTO();
        dto.setId(entity.getId());
        dto.setJobId(entity.getJob().getId());
        dto.setJobTitle(entity.getJob().getTitle());
        dto.setCandidateId(entity.getCandidate().getId());
        dto.setCandidateName(entity.getCandidate().getName());
        dto.setCandidateEmail(entity.getCandidate().getEmail());
        dto.setStatus(entity.getStatus());
        dto.setCoverNote(entity.getCoverNote());
        dto.setRating(entity.getRating());
        dto.setAppliedAt(entity.getAppliedAt());
        return dto;
    }

    public Application toEntity(ApplicationRequestDTO dto) {
        Application entity = new Application();
        entity.setCoverNote(dto.getCoverNote());
        return entity;
    }

}
