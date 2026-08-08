package com.noobdevs.talentbridge_ats.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecruiterResponseDTO extends UserResponseDTO{
    private String recruiterType;

    public RecruiterResponseDTO(Long id, String name, String email, String recruiterType) {
        super(id, name, email);
        this.recruiterType = recruiterType;
    }
}
