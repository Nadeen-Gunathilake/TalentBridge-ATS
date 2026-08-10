package com.noobdevs.talentbridge_ats.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationNoteResponseDTO {
    private Long id;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
}
