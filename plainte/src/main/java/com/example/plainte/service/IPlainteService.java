package com.example.plainte.service;

import com.example.plainte.dto.PlainteDto;

import java.util.List;

public interface IPlainteService {

    PlainteDto save(PlainteDto plainteDto);
    PlainteDto byId(Long id);
    List<PlainteDto> all(int pageNo, int pageSize);
    PlainteDto update(Long id,  PlainteDto plainteDto);
    void delete(Long id);
}
