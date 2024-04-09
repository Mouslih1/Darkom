package com.example.plainte.service;

import com.example.plainte.dto.PlainteDto;

import java.util.List;

public interface IPlainteService {

    PlainteDto save(Long agenceId,Long userCreatedNotification ,PlainteDto plainteDto, String authorization);
    PlainteDto byId(Long id);
    List<PlainteDto> all(int pageNo, int pageSize);
    PlainteDto byIdByAgence(Long id, Long agenceId);
    List<PlainteDto> allByAgence(Long agenceId, int pageNo, int pageSize);
    PlainteDto update(Long id,  PlainteDto plainteDto);
    void delete(Long id);
}
