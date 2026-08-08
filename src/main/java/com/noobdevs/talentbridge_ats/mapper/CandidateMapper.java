package com.noobdevs.talentbridge_ats.mapper;


import com.noobdevs.talentbridge_ats.dto.CandidateRequestDTO;
import com.noobdevs.talentbridge_ats.dto.CandidateResponseDTO;
import com.noobdevs.talentbridge_ats.models.Candidate;
import com.noobdevs.talentbridge_ats.models.User;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    public CandidateResponseDTO toResponseDTO(Candidate entity) {
        CandidateResponseDTO dto = new CandidateResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());

        return dto;

    }

    public Candidate toEntity(CandidateRequestDTO dto) {
        Candidate entity = new Candidate();

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }


}
