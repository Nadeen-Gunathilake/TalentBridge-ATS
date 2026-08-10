package com.noobdevs.talentbridge_ats.mapper;

import com.noobdevs.talentbridge_ats.dto.RecruiterRequestDTO;
import com.noobdevs.talentbridge_ats.dto.RecruiterResponseDTO;
import com.noobdevs.talentbridge_ats.models.Recruiter;
import org.springframework.stereotype.Component;

@Component
public class RecruiterMapper {

    public RecruiterResponseDTO toResponseDTO(Recruiter entity) {
        RecruiterResponseDTO dto = new RecruiterResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRecruiterType(entity.getRecruiterType());

        return dto;

    }

    public Recruiter toEntity(RecruiterRequestDTO dto) {
        Recruiter entity = new Recruiter();

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRecruiterType(dto.getRecruiterType());

        return entity;
    }

}
