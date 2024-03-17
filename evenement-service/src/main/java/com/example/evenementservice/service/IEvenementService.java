package com.example.evenementservice.service;

import com.example.evenementservice.dto.EvenementDto;

import java.util.List;

public interface IEvenementService {

    EvenementDto save(EvenementDto evenementDto);
    EvenementDto update(Long id, EvenementDto evenementDto);
    void delete(Long id);
    List<EvenementDto> all();
    EvenementDto byId(Long id);
}
