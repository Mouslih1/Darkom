package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;

import java.util.List;


public interface IUserService {

    UserResponse save(Long agentCreatedBy , UserRequest userRequest);
    UserResponse getById(Long id);

    List<UserResponse> all();

    UserDto update(Long id,UserDto userDTO);

    void delete(Long id);
    List<UserResponse> allByAgence(Long agenceId);
    UserResponse byIdAndAgence(Long userId, Long agenceId);
    UserResponse saveByAdmin(Long agentCreatedBy, UserRequest userDto);
}
