package com.noobdevs.talentbridge_ats.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationNoteRequestDTO {

    @NotBlank(message = "Note content is required")
    @Size(max = 2000, message = "Note must be under 2000 characters")
    private String content;
}
