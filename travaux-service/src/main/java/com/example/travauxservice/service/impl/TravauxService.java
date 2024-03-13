package com.example.travauxservice.service.impl;

import com.example.travauxservice.dto.TravauxDto;
import com.example.travauxservice.repository.ITravauxRepository;
import com.example.travauxservice.service.ITravauxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravauxService implements ITravauxService {

    private final ModelMapper modelMapper;
    private final ITravauxRepository iTravauxRepository;

    @Override
    public TravauxDto save(TravauxDto travauxDto)
    {

        return null;
    }

    @Override
    public TravauxDto update(Long id, TravauxDto travauxDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TravauxDto> all() {
        return null;
    }

    @Override
    public TravauxDto byId(Long id) {
        return null;
    }
}
