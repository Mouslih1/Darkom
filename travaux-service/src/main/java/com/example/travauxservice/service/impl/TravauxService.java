package com.example.travauxservice.service.impl;

import com.example.travauxservice.dto.TravauxDto;
import com.example.travauxservice.dto.TravauxProducerDto;
import com.example.travauxservice.entity.Travaux;
import com.example.travauxservice.exception.NotFoundException;
import com.example.travauxservice.producers.TravauxProducer;
import com.example.travauxservice.repository.ITravauxRepository;
import com.example.travauxservice.service.ITravauxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravauxService implements ITravauxService {

    private final ModelMapper modelMapper;
    private final ITravauxRepository iTravauxRepository;
    private static final String TRAVAUX_NOT_FOUND = "Travaux not found with this id : ";
    private static final String TRAVAUX_OR_AGENCE_NOT_FOUND = "Travaux or agence not found with this id : ";
    private final TravauxProducer travauxProducer;


    @Override
    public TravauxDto save(Long agenceId,Long userCreateNotification ,TravauxDto travauxDto, String authorization)
    {
        travauxDto.setAgenceId(agenceId);
        Travaux travaux = iTravauxRepository.save(modelMapper.map(travauxDto, Travaux.class));

        travauxProducer.producerMessage(
                new TravauxProducerDto(
                        travaux.getId(),
                        "Create an travaux",
                        travaux.getSyndecCreatedBy(),
                        userCreateNotification,
                        null,
                        travaux.getAgenceId(),
                        authorization
                )
        );

        return modelMapper.map(travaux, TravauxDto.class);
    }

    @Override
    public TravauxDto update(Long id, TravauxDto travauxDto)
    {
        Travaux travaux = iTravauxRepository.findById(id).orElseThrow(() -> new NotFoundException(TRAVAUX_NOT_FOUND + id));
        travaux.setEtat(travauxDto.getEtat());
        travaux.setDescription(travauxDto.getDescription());
        travaux.setImmeubleId(travauxDto.getImmeubleId());
        travaux.setDateDebut(travauxDto.getDateDebut());
        travaux.setDateFin(travauxDto.getDateFin());
        travaux.setMontant(travauxDto.getMontant());

        Travaux travauxUpdated = iTravauxRepository.save(travaux);
        return modelMapper.map(travauxUpdated, TravauxDto.class );
    }

    @Override
    public void delete(Long id)
    {
        iTravauxRepository.deleteById(id);
    }

    @Override
    public List<TravauxDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Travaux> travauxes = iTravauxRepository.findAll(pageable);

        return travauxes
                .stream()
                .map((travaux) -> modelMapper.map(travaux, TravauxDto.class))
                .toList();
    }

    @Override
    public TravauxDto byId(Long id)
    {
        Travaux travaux = iTravauxRepository.findById(id).orElseThrow(() -> new NotFoundException(TRAVAUX_NOT_FOUND + id));
        return modelMapper.map(travaux, TravauxDto.class);
    }

    @Override
    public List<TravauxDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<Travaux> travauxes = iTravauxRepository.findByAgenceId(agenceId);

        List<Travaux> paginatedTravauxs = travauxes.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedTravauxs
                .stream()
                .map((travaux) -> modelMapper.map(travaux, TravauxDto.class))
                .toList();
    }

    @Override
    public TravauxDto byIdByAgence(Long travauxId, Long agenceId)
    {
        Travaux travaux = iTravauxRepository.findByIdAndAgenceId(travauxId, agenceId)
                .orElseThrow(() -> new NotFoundException(TRAVAUX_OR_AGENCE_NOT_FOUND + travauxId + "agence : " + agenceId));
        return modelMapper.map(travaux, TravauxDto.class);
    }
}
