package com.noobdevs.talentbridge_ats.dto;

import com.noobdevs.talentbridge_ats.enums.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobRequestDTO {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "work mode is required")
    private String work_mode;

    @NotBlank(message = "employment type is required")
    private String employment_type;

    @NotBlank(message = "salary range is required")
    private String salary_range;

    @NotBlank(message = "Skills required")
    private String required_skills;

    @NotNull(message = "Closing date is required")
    private LocalDate closing_date;

    @NotNull(message = "Status is required")
    private JobStatus status;

}
