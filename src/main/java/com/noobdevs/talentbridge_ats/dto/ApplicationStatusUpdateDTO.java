package com.noobdevs.talentbridge_ats.dto;

import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationStatusUpdateDTO {
    @NotNull(message = "Status is required")
    private ApplicationStatus status;
}
