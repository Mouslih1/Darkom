package com.example.evenementservice.service.impl;

import com.example.evenementservice.dto.EvenementDto;
import com.example.evenementservice.entity.Evenement;
import com.example.evenementservice.exception.NotFoundException;
import com.example.evenementservice.repository.IEvenementRepository;
import com.example.evenementservice.service.IEvenementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvenementService implements IEvenementService {

    private final IEvenementRepository iEvenementRepository;
    private final ModelMapper modelMapper;
    private static final String EVENEMENT_NOT_FOUND = "Evenement not found with this id : ";
    private static final String EVENEMENT_OR_AGENCE_NOT_FOUND = "Evenement or agence not found with this id : ";


    @Override
    public EvenementDto save(Long agenceId, EvenementDto evenementDto)
    {
        evenementDto.setAgenceId(agenceId);
        Evenement evenement = iEvenementRepository.save(modelMapper.map(evenementDto, Evenement.class));
        return modelMapper.map(evenement, EvenementDto.class);
    }

    @Override
    public EvenementDto update(Long id, EvenementDto evenementDto)
    {
        Evenement evenement = iEvenementRepository.findById(id).orElseThrow(() -> new NotFoundException(EVENEMENT_NOT_FOUND + id));
        evenement.setAppartementId(evenementDto.getAppartementId());
        evenement.setSujet(evenementDto.getSujet());
        evenement.setDescription(evenementDto.getDescription());
        evenement.setDateEvenement(evenementDto.getDateEvenement());

        Evenement evenementUpdated = iEvenementRepository.save(evenement);
        return modelMapper.map(evenementUpdated, EvenementDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iEvenementRepository.deleteById(id);
    }

    @Override
    public List<EvenementDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Evenement> evenements = iEvenementRepository.findAll(pageable);

        return evenements
                .stream()
                .map((element) -> modelMapper.map(element, EvenementDto.class))
                .toList();
    }

    @Override
    public EvenementDto byId(Long id)
    {
        Evenement evenement = iEvenementRepository.findById(id).orElseThrow(() -> new NotFoundException(EVENEMENT_NOT_FOUND + id));
        return modelMapper.map(evenement, EvenementDto.class);
    }

    @Override
    public List<EvenementDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<Evenement> evenements = iEvenementRepository.findByAgenceId(agenceId);

        List<Evenement> paginatedEvenements = evenements.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedEvenements
                .stream()
                .map((evenement) -> modelMapper.map(evenement, EvenementDto.class))
                .toList();
    }

    @Override
    public EvenementDto byIdByAgence(Long id, Long agenceId)
    {
        Evenement evenement = iEvenementRepository.findByIdAndAgenceId(id, agenceId)
                .orElseThrow(() -> new NotFoundException(EVENEMENT_OR_AGENCE_NOT_FOUND + id + "agence : " + agenceId));
        return modelMapper.map(evenement, EvenementDto.class);
    }
}
