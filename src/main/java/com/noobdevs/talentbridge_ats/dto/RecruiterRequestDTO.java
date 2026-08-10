package com.noobdevs.talentbridge_ats.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecruiterRequestDTO extends UserRequestDTO{

    @NotBlank(message = "recruiter type is required")
    private String recruiterType;

    public RecruiterRequestDTO(String name, String email, String password,String recruiterType) {
        super(name, email, password);
        this.recruiterType=recruiterType;
    }

}
