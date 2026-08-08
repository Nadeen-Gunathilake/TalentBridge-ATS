package com.noobdevs.talentbridge_ats.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandidateResponseDTO extends UserResponseDTO{
    public CandidateResponseDTO(Long id, String name, String email) {
        super(id, name, email);
    }
}
