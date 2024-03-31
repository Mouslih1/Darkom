package com.example.userservice.services.impls;

import com.example.userservice.clients.MediaClient;
import com.example.userservice.dtos.*;
import com.example.userservice.entities.User;
import com.example.userservice.entities.enums.MediaStatus;
import com.example.userservice.entities.enums.Role;
import com.example.userservice.exceptions.NotFoundException;
import com.example.userservice.exceptions.ValidationException;
import com.example.userservice.repositories.IUserRepository;
import com.example.userservice.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;
    private final static String USER_OR_AGENCE_NOT_FOUND = "User or agence not found with this id's : ";
    private final static String USER_NOT_FOUND = "User not found with this id : ";
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse save(Long agenceId , UserRequest userRequest)
    {
        emailSenderService.sendEmail(
                userRequest.getEmail(),
                "Generate password.",
                "This is your password : " + userRequest.getPassword()
        );

        userRequest.setAgenceId(agenceId);
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return saveUserAndMedia(userRequest);
    }

    public UserResponse saveUserAndMedia(UserRequest userRequest)
    {
        User user = userRepository.save(modelMapper.map(userRequest, User.class));
        UserResponse userResponse = new UserResponse();
        userResponse.setUserDto(modelMapper.map(user, UserDto.class));

        if(userRequest.getMultipartFiles() != null && !userRequest.getMultipartFiles().isEmpty())
        {
            userResponse.setMedias(mediaClient.save(userRequest.getMultipartFiles(),
                    user.getId(), MediaStatus.PHOTO_PROFIL).getBody());
        }

        return userResponse;
    }

    @Override
    public UserResponse getById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(id, MediaStatus.PHOTO_PROFIL).getBody();

        return new UserResponse(modelMapper.map(user, UserDto.class), medias);
    }

    @Override
    public List<UserResponse> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);

        return users
                .stream()
                .map((user) -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(userDto.getId(), MediaStatus.PHOTO_PROFIL).getBody();
                    return new UserResponse(userDto, medias);
                })
                .toList();
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());

        if(userRequest.getRole().equals(Role.ADMIN))
        {
            throw new ValidationException("You can't add user in Admin state !");
        }else{
            user.setRole(userRequest.getRole());
        }

        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setDateNaissance(userRequest.getDateNaissance());
        user.setTelephone(userRequest.getTelephone());
        user.setUsername(userRequest.getUsername());

        User userUpdated = userRepository.save(user);
        List<MediaDto> mediaDto = mediaClient.getMediaByRelatedId(userUpdated.getId(), MediaStatus.PHOTO_PROFIL).getBody();
        return new UserResponse(modelMapper.map(userUpdated, UserDto.class), mediaDto);
    }

    @Override
    public UserResponse updateByAdmin(Long id, UserRequest userRequest)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));

        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setDateNaissance(userRequest.getDateNaissance());
        user.setTelephone(userRequest.getTelephone());
        user.setUsername(userRequest.getUsername());
        user.setAgenceId(userRequest.getAgenceId());

        User userUpdated = userRepository.save(user);
        List<MediaDto> mediaDto = mediaClient.getMediaByRelatedId(userUpdated.getId(), MediaStatus.PHOTO_PROFIL).getBody();
        return new UserResponse(modelMapper.map(userUpdated, UserDto.class), mediaDto);    }

    @Override
    public UserResponse updatePhotoProfil(Long id, UserRequestLogo userRequestLogo)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        List<MediaDto> mediaDto = new ArrayList<>();

        if(userRequestLogo.getMultipartFiles() != null && !userRequestLogo.getMultipartFiles().isEmpty())
        {
            mediaDto = mediaClient.update(userRequestLogo.getMultipartFiles(),
                     user.getId(), MediaStatus.PHOTO_PROFIL).getBody();
        }

        return new UserResponse(modelMapper.map(user, UserDto.class), mediaDto);
    }

    @Override
    public void delete(Long id)
    {
        userRepository.deleteById(id);
        mediaClient.deleteMediaByRelatedId(id, MediaStatus.PHOTO_PROFIL);
    }

    @Override
    public List<UserResponse> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<User> users = userRepository.findByAgenceId(agenceId);

        List<User> paginatedUsers = users.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedUsers
                .stream()
                .map((user) -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(userDto.getId(), MediaStatus.PHOTO_PROFIL).getBody();
                    return new UserResponse(userDto, medias);
                })
                .toList();
    }

    @Override
    public UserResponse byIdAndAgence(Long userId, Long agenceId)
    {
        User user = userRepository.findByIdAndAgenceId(userId, agenceId)
                .orElseThrow(() -> new NotFoundException(USER_OR_AGENCE_NOT_FOUND + userId + "agence : " + agenceId));
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(user.getId(), MediaStatus.PHOTO_PROFIL).getBody();
        return new UserResponse(modelMapper.map(user, UserDto.class), medias);
    }

    @Override
    public UserResponse saveByAdmin(UserRequest userRequest)
    {
        emailSenderService.sendEmail(
                userRequest.getEmail(),
                "Generate password.",
                "This is your password : " + userRequest.getPassword()
        );

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return saveUserAndMedia(userRequest);
    }

    @Override
    public boolean updatePassword(Long id, UserPasswordDto userPasswordDto)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));

        if(passwordEncoder.matches(userPasswordDto.getCurrentPassword(), user.getPassword()))
        {
            emailSenderService.sendEmail(
                    user.getEmail(),
                    "Take you new password.",
                    "This is your new password : " + userPasswordDto.getNewPassword()
            );

            user.setPassword(passwordEncoder.encode(userPasswordDto.getNewPassword()));
            userRepository.save(user);

            return true;
        }

        return false;
    }
}
