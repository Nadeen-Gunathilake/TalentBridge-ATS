package com.noobdevs.talentbridge_ats.dto;

import com.noobdevs.talentbridge_ats.enums.JobStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String work_mode;
    private String employment_type;
    private String salary_range;
    private String required_skills;
    private LocalDate closing_date;
    private JobStatus status;
}
