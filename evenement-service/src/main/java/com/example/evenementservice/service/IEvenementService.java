package com.example.evenementservice.service;

import com.example.evenementservice.dto.EvenementDto;

import java.util.List;

public interface IEvenementService {

    EvenementDto save(Long agenceId,Long userCreateNotification ,EvenementDto evenementDto,String authorization);
    EvenementDto update(Long id, EvenementDto evenementDto);
    void delete(Long id);
    List<EvenementDto> all(int pageNo, int pageSize);
    EvenementDto byId(Long id);
    List<EvenementDto> allByAgence(Long agenceId,int pageNo, int pageSize);
    EvenementDto byIdByAgence(Long id, Long agenceId);
}
