package com.example.notificationservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private UserDto userDto;
    private List<MediaDto> medias;
}
