package com.noobdevs.talentbridge_ats.mapper;


import com.noobdevs.talentbridge_ats.dto.UserRequestDTO;
import com.noobdevs.talentbridge_ats.dto.UserResponseDTO;
import com.noobdevs.talentbridge_ats.models.Job;
import com.noobdevs.talentbridge_ats.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User entity) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        return dto;

    }

    public User toEntity(UserRequestDTO dto) {
        User entity = new User();

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }


}
