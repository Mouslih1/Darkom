package com.example.travauxservice.service;

import com.example.travauxservice.dto.TravauxDto;

import java.util.List;

public interface ITravauxService {

    TravauxDto save(TravauxDto travauxDto);
    TravauxDto update(Long id, TravauxDto travauxDto);
    void delete(Long id);
    List<TravauxDto> all(int pageNo, int pageSize);
    TravauxDto byId(Long id);
}
