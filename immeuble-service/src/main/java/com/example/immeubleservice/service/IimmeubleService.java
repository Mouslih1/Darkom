package com.example.immeubleservice.service;

import com.example.immeubleservice.dto.ImmeubleDto;

import java.util.List;

public interface IimmeubleService {

    ImmeubleDto save(ImmeubleDto immeubleDto);
    ImmeubleDto update(Long id, ImmeubleDto immeubleDto);
    List<ImmeubleDto> all();
    ImmeubleDto byId(Long id);
    void delete(Long id);
}
