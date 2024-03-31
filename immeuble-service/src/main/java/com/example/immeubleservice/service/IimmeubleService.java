package com.example.immeubleservice.service;

import com.example.immeubleservice.dto.ImmeubleDto;
import com.example.immeubleservice.entity.enums.StatusImmeuble;

import java.util.List;

public interface IimmeubleService {

    ImmeubleDto save(Long agenceId, ImmeubleDto immeubleDto);
    ImmeubleDto update(Long id, ImmeubleDto immeubleDto);
    List<ImmeubleDto> all(int pageNo, int pageSize);
    ImmeubleDto byId(Long id);
    List<ImmeubleDto> allByAgence(Long agenceId, int pageNo, int pageSize);
    ImmeubleDto byIdAndAgence(Long id, Long agenceId);
    void delete(Long id);
    void updateEtatImmeuble(Long id, StatusImmeuble statusImmeuble);
}
