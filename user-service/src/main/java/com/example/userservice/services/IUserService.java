package com.example.userservice.services;

import com.example.userservice.dtos.*;

import java.util.List;


public interface IUserService {

    UserResponse save(Long agenceId,UserRequest userRequest);
    UserResponse getById(Long id);
    List<UserResponse> all(int pageNo, int pageSize);
    UserResponse update(Long id,  UserRequest userRequest);
    UserResponse updatePhotoProfil(Long id, UserRequestLogo userRequestLogo);
    void delete(Long id);
    List<UserResponse> allByAgence(Long agenceId);
    UserResponse byIdAndAgence(Long userId, Long agenceId);
    UserResponse saveByAdmin(UserRequest userDto);

    boolean updatePassword(Long id, UserPasswordDto userPasswordDto);
}
