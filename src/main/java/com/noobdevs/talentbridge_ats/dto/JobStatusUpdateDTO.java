package com.noobdevs.talentbridge_ats.dto;

import com.noobdevs.talentbridge_ats.enums.JobStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobStatusUpdateDTO {
    @NotNull(message = "Status is required")
    private JobStatus status;
}
