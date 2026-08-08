package com.noobdevs.talentbridge_ats.dto;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequestDTO {

    @Size(max = 2000, message = "Cover note must be under 2000 characters")
    private String coverNote;
}
