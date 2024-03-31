package com.example.appartementservice.service;

import com.example.appartementservice.dto.AppartementDto;

import java.util.List;

public interface IAppartementService {

    AppartementDto save(Long agenceId, AppartementDto appartementDto);
    AppartementDto update(Long id, AppartementDto appartementDto);
    List<AppartementDto> all(int pageNo, int pageSize);
    List<AppartementDto> allByAgence(Long agenceId,int pageNo, int pageSize);

    AppartementDto byId(Long id);
    AppartementDto byIdAndAgence(Long id, Long agenceId);
    void delete(Long id);
    List<AppartementDto> byIdAndImmeuble(Long immeubleId);
    void updateStatusImmeuble(Long immeubleId);
    boolean areAnyApartmentsFree(Long immeubleId);
    void validation(Long immeubleId);
    int countOfAppartementInImmeuble(Long immeubleId);
    void updateEtatAppartementToOccuper(Long appartementId);
}
