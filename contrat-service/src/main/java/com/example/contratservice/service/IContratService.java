package com.example.contratservice.service;

import com.example.contratservice.dto.AppartementDto;
import com.example.contratservice.dto.ContratDto;

import java.util.List;

public interface IContratService {

    ContratDto save(Long agenceId, ContratDto contratDto);
    ContratDto byId(Long id);
    List<ContratDto> all(int pageNo, int pageSize);
    ContratDto byIdAndAgence(Long contratId, Long agenceId);
    List<ContratDto> allByAgence(Long agenceId, int pageNo, int pageSize);
    ContratDto update(Long id,  ContratDto contratDto);
    void delete(Long id);
    void updateStatusImmeuble(Long immeubleId);
    void getTypeOfContratAndMontantOfContrat(AppartementDto appartementDto, ContratDto contratDto);
    boolean areAnyApartmentsFree(Long immeubleId);
    boolean existAppartement(Long appartementId);
}
