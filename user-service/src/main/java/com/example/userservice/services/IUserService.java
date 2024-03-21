package com.example.userservice.services;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.dtos.UserRequest;
import com.example.userservice.dtos.UserRequestLogo;
import com.example.userservice.dtos.UserResponse;

import java.util.List;


public interface IUserService {

    UserResponse save(Long agenceId,UserRequest userRequest);
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
