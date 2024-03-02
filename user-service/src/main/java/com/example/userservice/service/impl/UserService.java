package com.example.userservice.service.impl;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.repository.IUserRepository;
import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final static String USER_NOT_FOUND = "User not found with this id : ";

    @Override
    public UserDto save(Long userCreatedBy, UserDto userDto)
    {
        userDto.setAgentCreatedBy(userCreatedBy);
        userDto.setCreatedAt(LocalDateTime.now());
        User user = userRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> all()
    {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto update(Long id, UserDto userDTO)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setDateNaissance(userDTO.getDateNaissance());
        user.setTelephone(userDTO.getTelephone());
        user.setUsername(user.getUsername());
        user.setVille(userDTO.getVille());

        User userUpdated = userRepository.save(user);
        return modelMapper.map(userUpdated, UserDto.class);
    }

    @Override
    public void delete(Long id)
    {
        userRepository.deleteById(id);
    }
}
