package com.noobdevs.talentbridge_ats.dto;

import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationResponseDTO {
    private Long id;
    private LocalDateTime appliedAt;
    private ApplicationStatus status;
}
