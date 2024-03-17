package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserRequestLogo;
import com.example.userservice.dto.UserResponse;

import java.util.List;


public interface IUserService {

    UserResponse save(UserRequest userRequest);
    UserResponse getById(Long id);
    List<UserResponse> all();
    UserResponse update(Long id,  UserRequest userRequest);
    UserResponse updateLogo(Long id, UserRequestLogo userRequestLogo);
    void delete(Long id);
    List<UserResponse> allByAgence(Long agenceId);
    UserResponse byIdAndAgence(Long userId, Long agenceId);
    UserResponse saveByAdmin(UserRequest userDto);
    void updatePassword(Long id, UserDto userDto);
}
