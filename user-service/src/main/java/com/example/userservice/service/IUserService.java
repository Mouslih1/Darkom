package com.example.userservice.service;

import com.example.userservice.dto.UserDto;

import java.util.List;


public interface IUserService {

    UserDto save(Long agentCreatedBy , UserDto userDto);
    UserDto getById(Long id);

    List<UserDto> all();

    UserDto update(Long id,UserDto userDTO);

    void delete(Long id);
}
