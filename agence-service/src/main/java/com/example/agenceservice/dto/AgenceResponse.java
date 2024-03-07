package com.example.agenceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenceResponse {

    private AgenceDto agenceDto;
    private List<MediaDto> medias;
}
