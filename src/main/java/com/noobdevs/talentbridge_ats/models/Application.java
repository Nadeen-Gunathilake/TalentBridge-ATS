package com.noobdevs.talentbridge_ats.models;

import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "job_table")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime appliedAt;
    private ApplicationStatus status;

}
