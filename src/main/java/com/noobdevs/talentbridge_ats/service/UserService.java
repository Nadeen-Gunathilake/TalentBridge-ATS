package com.noobdevs.talentbridge_ats.service;
import com.noobdevs.talentbridge_ats.dto.UserRequestDTO;
import com.noobdevs.talentbridge_ats.dto.UserResponseDTO;
import com.noobdevs.talentbridge_ats.enums.JobStatus;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO updateUser(Long id,UserRequestDTO dto);
    void deleteUser(Long id);

}
