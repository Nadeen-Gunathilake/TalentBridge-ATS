package com.noobdevs.talentbridge_ats.service.impl;
import com.noobdevs.talentbridge_ats.dto.UserRequestDTO;
import com.noobdevs.talentbridge_ats.dto.UserResponseDTO;
import com.noobdevs.talentbridge_ats.mapper.UserMapper;
import com.noobdevs.talentbridge_ats.models.User;
import com.noobdevs.talentbridge_ats.repository.UserRepository;
import com.noobdevs.talentbridge_ats.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toResponseDTO(user);

    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto){
        User user=userMapper.toEntity(dto);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(Long id,UserRequestDTO dto){
        User existing=userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("User not found with id: "+ id));
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());

        return userMapper.toResponseDTO(userRepository.save(existing));
    }

    @Override
    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.deleteById(id);
    }


}
