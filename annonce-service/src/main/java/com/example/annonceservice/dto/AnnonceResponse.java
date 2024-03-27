package com.example.annonceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceResponse {

    private AnnonceDto annonceDto;
    private List<MediaDto> medias;
}
