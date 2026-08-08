package com.noobdevs.talentbridge_ats.dto;

import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationRecruiterViewDTO {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private ApplicationStatus status;
    private String coverNote;
    private Integer rating;
    private LocalDateTime appliedAt;
}
