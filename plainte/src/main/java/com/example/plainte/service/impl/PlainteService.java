package com.example.plainte.service.impl;

import com.example.plainte.dto.PlainteDto;
import com.example.plainte.entity.Plainte;
import com.example.plainte.exception.NotFoundException;
import com.example.plainte.repository.IPlainteRepository;
import com.example.plainte.service.IPlainteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlainteService implements IPlainteService {

    private final IPlainteRepository iPlainteRepository;
    private final ModelMapper modelMapper;
    private static final String PLAINTE_NOT_FOUND = "Plainte not found this id : ";

    @Override
    public PlainteDto save(PlainteDto plainteDto)
    {
        Plainte plainte = iPlainteRepository.save(modelMapper.map(plainteDto, Plainte.class));
        return modelMapper.map(plainte, PlainteDto.class);
    }

    @Override
    public PlainteDto byId(Long id)
    {
        Plainte plainte = iPlainteRepository.findById(id).orElseThrow(() -> new NotFoundException(PLAINTE_NOT_FOUND + id));
        return modelMapper.map(plainte, PlainteDto.class);
    }

    @Override
    public List<PlainteDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Plainte> plaintes = iPlainteRepository.findAll(pageable);

        return plaintes
                .stream()
                .map((plainte) -> modelMapper.map(plainte, PlainteDto.class))
                .toList();
    }

    @Override
    public PlainteDto update(Long id, PlainteDto plainteDto)
    {
        Plainte plainte = iPlainteRepository.findById(id).orElseThrow(() -> new NotFoundException(PLAINTE_NOT_FOUND + id));
        plainte.setSujet(plainteDto.getSujet());
        plainte.setDescription(plainteDto.getDescription());
        plainte.setUrgence(plainteDto.getUrgence());
        plainte.setStatusPlainte(plainteDto.getStatusPlainte());

        Plainte plainteUpdated = iPlainteRepository.save(plainte);
        return modelMapper.map(plainteUpdated, PlainteDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iPlainteRepository.deleteById(id);
    }
}
