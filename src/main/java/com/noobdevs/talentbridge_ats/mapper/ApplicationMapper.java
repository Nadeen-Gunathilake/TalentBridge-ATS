package com.noobdevs.talentbridge_ats.mapper;

import com.noobdevs.talentbridge_ats.dto.ApplicationRequestDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.dto.JobRequestDTO;
import com.noobdevs.talentbridge_ats.dto.JobResponseDTO;
import com.noobdevs.talentbridge_ats.models.Application;
import com.noobdevs.talentbridge_ats.models.Job;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationResponseDTO toResponseDTO(Application entity) {
        ApplicationResponseDTO dto = new ApplicationResponseDTO();
        dto.setId(entity.getId());
        dto.setAppliedAt(entity.getAppliedAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public Application toEntity(ApplicationRequestDTO dto) {
        Application entity = new Application();

        entity.setAppliedAt(dto.getAppliedAt());
        entity.setStatus(dto.getStatus());
        return entity;
    }

}
