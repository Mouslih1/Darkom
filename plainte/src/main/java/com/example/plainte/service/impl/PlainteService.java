package com.example.plainte.service.impl;

import com.example.plainte.dto.PlainteDto;
import com.example.plainte.dto.PlainteProducerDto;
import com.example.plainte.entity.Plainte;
import com.example.plainte.exception.NotFoundException;
import com.example.plainte.producers.PlainteProducer;
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
    private static final String PLAINTE_OR_AGENCE_NOT_FOUND = "Plainte or agence not found this id : ";
    private final PlainteProducer plainteProducer;


    @Override
    public PlainteDto save(Long agenceId,Long userCreatedNotification ,PlainteDto plainteDto, String authorization)
    {
        plainteDto.setAgenceId(agenceId);
        Plainte plainte = iPlainteRepository.save(modelMapper.map(plainteDto, Plainte.class));

        plainteProducer.producerMessage(
                new PlainteProducerDto(
                        plainte.getId(),
                        "Create an complaint",
                        plainte.getPropreitaireCreatedBy(),
                        userCreatedNotification,
                        null,
                        plainte.getAgenceId(),
                        authorization)
        );

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
    public PlainteDto byIdByAgence(Long plainteId, Long agenceId)
    {
        Plainte plainte = iPlainteRepository.findByIdAndAgenceId(plainteId, agenceId)
                .orElseThrow(() -> new NotFoundException(PLAINTE_OR_AGENCE_NOT_FOUND + plainteId + "agence : " + agenceId));
        return modelMapper.map(plainte, PlainteDto.class);
    }

    @Override
    public List<PlainteDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<Plainte> plaintes = iPlainteRepository.findByAgenceId(agenceId);

        List<Plainte> paginatedPlaintes = plaintes.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedPlaintes
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
