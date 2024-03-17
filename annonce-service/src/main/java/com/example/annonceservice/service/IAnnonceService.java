package com.example.annonceservice.service;

import com.example.annonceservice.dto.AnnonceDto;

import java.util.List;

public interface IAnnonceService {

    AnnonceDto save(AnnonceDto annonceDto);
    AnnonceDto byId(Long id);
    List<AnnonceDto> all();
    AnnonceDto update(Long id,  AnnonceDto annonceDto);
    void delete(Long id);
}
