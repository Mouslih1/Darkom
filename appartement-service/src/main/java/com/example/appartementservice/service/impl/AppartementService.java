package com.example.appartementservice.service.impl;

import com.example.appartementservice.dto.AppartementDto;
import com.example.appartementservice.entity.Appartement;
import com.example.appartementservice.repository.IAppartementRepository;
import com.example.appartementservice.service.IAppartementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AppartementService implements IAppartementService {

    private final ModelMapper modelMapper;
    private final IAppartementRepository iAppartementRepository;

    @Override
    public AppartementDto save(AppartementDto appartementDto)
    {
        Appartement appartement = iAppartementRepository.save(modelMapper.map(appartementDto, Appartement.class));
        return modelMapper.map(appartement, AppartementDto.class);
    }

    @Override
    public AppartementDto update(Long id, AppartementDto appartementDto) {
        return null;
    }

    @Override
    public List<AppartementDto> all() {
        return null;
    }

    @Override
    public AppartementDto byId(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
