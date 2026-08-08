package com.noobdevs.talentbridge_ats.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandidateRequestDTO extends UserRequestDTO{
    public CandidateRequestDTO(String name, String email, String password) {
        super(name, email, password);
    }
}
