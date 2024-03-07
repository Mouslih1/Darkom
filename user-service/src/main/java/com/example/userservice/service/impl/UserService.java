package com.example.userservice.service.impl;

import com.example.userservice.client.MediaClient;
import com.example.userservice.dto.MediaDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.entity.enums.MediaStatus;
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
    private final MediaClient mediaClient;
    private final static String USER_OR_AGENCE_NOT_FOUND = "User or agence not found with this id's : ";
    private final static String USER_NOT_FOUND = "User not found with this id : ";

    @Override
    public UserResponse save(/* Long agenceId , */Long agentCreatedBy, UserRequest userRequest)
    {
        userRequest.setAgentCreatedBy(agentCreatedBy);
        userRequest.setCreatedAt(LocalDateTime.now());
        userRequest.setAgenceId(1L);
        return saveUserAndMedia(agentCreatedBy, userRequest);
    }

    @Override
    public UserResponse getById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(id).getBody();

        return new UserResponse(modelMapper.map(user, UserDto.class), medias);
    }

    @Override
    public List<UserResponse> all()
    {
        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map((user) -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(userDto.getId()).getBody();
                    return new UserResponse(userDto, medias);
                })
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
        user.setUpdatedAt(LocalDateTime.now());

        User userUpdated = userRepository.save(user);
        return modelMapper.map(userUpdated, UserDto.class);
    }

    @Override
    public void delete(Long id)
    {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> allByAgence(Long agenceId)
    {
        List<User> users = userRepository.findByAgenceId(agenceId);
        return users
                .stream()
                .map((user) -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(userDto.getId()).getBody();
                    return new UserResponse(userDto, medias);
                })
                .toList();
    }

    @Override
    public UserResponse byIdAndAgence(Long userId, Long agenceId)
    {
        User user = userRepository.findByIdAndAgenceId(userId, agenceId).orElseThrow(() -> new NotFoundException(USER_OR_AGENCE_NOT_FOUND + userId + "agence : " + agenceId));
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(user.getId()).getBody();
        return new UserResponse(modelMapper.map(user, UserDto.class), medias);
    }

    @Override
    public UserResponse saveByAdmin(Long agentCreatedBy, UserRequest userRequest)
    {
        userRequest.setAgentCreatedBy(agentCreatedBy);
        userRequest.setCreatedAt(LocalDateTime.now());
        userRequest.setAgenceId(1L);
        return saveUserAndMedia(agentCreatedBy, userRequest);
    }

    public UserResponse saveUserAndMedia(Long agentCreatedBy, UserRequest userRequest)
    {
        User user = userRepository.save(modelMapper.map(userRequest, User.class));
        UserResponse userResponse = new UserResponse();
        userResponse.setUserDto(modelMapper.map(userRequest, UserDto.class));
        if(userRequest.getMultipartFiles() != null && !userRequest.getMultipartFiles().isEmpty())
        {
            userResponse.setMedias(mediaClient.save(userRequest.getMultipartFiles(),
                    agentCreatedBy, user.getId(), MediaStatus.PHOTO_PROFIL).getBody());
        }
        return userResponse;
    }
}
