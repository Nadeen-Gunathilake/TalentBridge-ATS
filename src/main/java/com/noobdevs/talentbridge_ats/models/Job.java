package com.noobdevs.talentbridge_ats.models;

import com.noobdevs.talentbridge_ats.enums.JobStatus;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "job_table")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private String work_mode;
    private String employment_type;
    private String salary_range;
    private String required_skills;
    private LocalDate closing_date;

    @Enumerated(EnumType.STRING)
    private JobStatus status;
}
