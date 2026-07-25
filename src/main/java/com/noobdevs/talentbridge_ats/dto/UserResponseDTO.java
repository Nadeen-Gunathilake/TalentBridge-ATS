package com.noobdevs.talentbridge_ats.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String Email;
    private String password;
    private String role;
}
