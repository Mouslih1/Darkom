package com.example.userservice.services;

import com.example.userservice.dtos.*;
import com.example.userservice.entities.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;


public interface IUserService {

    UserResponse save(Long agenceId,UserRequest userRequest);
    UserResponse getById(Long id);
    List<UserResponse> all(int pageNo, int pageSize);
    UserResponse update(Long id,  UserUpdateRequest userUpdateRequest);
    UserResponse updateByAdmin(Long id,  UserUpdateRequest userUpdateRequest);
    UserResponse updatePhotoProfil(Long id, UserRequestLogo userRequestLogo);
    void delete(Long id);
    List<UserResponse> allByAgence(Long agenceId, int pageNo, int pageSize);
    List<UserResponse> getUsersByAgenceForNotifie(Long agenceId);
    UserResponse byIdAndAgence(Long userId, Long agenceId);
    UserResponse saveByAdmin(UserRequest userDto);
    boolean updatePassword(Long id, UserPasswordDto userPasswordDto);
    void forgotPassword(String email) throws MessagingException, UnsupportedEncodingException;
    void setPassword(String email, String newPassword);
    User byUsername(String username);
    UserResponse getByUsername(String username);
}
