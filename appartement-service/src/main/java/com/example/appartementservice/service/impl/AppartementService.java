package com.example.appartementservice.service.impl;

import com.example.appartementservice.client.ImmeubleClient;
import com.example.appartementservice.dto.AppartementDto;
import com.example.appartementservice.entity.Appartement;
import com.example.appartementservice.entity.enums.EtatAppartement;
import com.example.appartementservice.entity.enums.StatusImmeuble;
import com.example.appartementservice.repository.IAppartementRepository;
import com.example.appartementservice.service.IAppartementService;
import jakarta.ws.rs.NotFoundException;
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
    private final ImmeubleClient immeubleClient;
    private final static  String APPARTEMENT_NOT_FOUND = "Appartement not found with this id : ";

    @Override
    public AppartementDto save(AppartementDto appartementDto)
    {
        Appartement appartementSaved = iAppartementRepository.save(modelMapper.map(appartementDto, Appartement.class));
        updateStatusImmeuble(appartementSaved.getImmeubleId());
        
        return modelMapper.map(appartementSaved, AppartementDto.class);
    }

    private void updateStatusImmeuble(Long immeubleId)
    {
        boolean anyApartmentFree = areAnyApartmentsFree(immeubleId);

        if(anyApartmentFree)
        {
            immeubleClient.updateStatusImmeuble(immeubleId, StatusImmeuble.NON_OCCUPER);
        }else
        {
            immeubleClient.updateStatusImmeuble(immeubleId, StatusImmeuble.OCCUPER);
        }
    }

    public boolean areAnyApartmentsFree(Long immeubleId)
    {
        List<Appartement> appartements = iAppartementRepository.findByImmeubleId(immeubleId);
        for (Appartement appartement : appartements) {
            if (appartement.getEtatAppartement().equals(EtatAppartement.LIBRE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AppartementDto update(Long id, AppartementDto appartementDto)
    {
        Appartement appartement = iAppartementRepository.findById(id).orElseThrow(() -> new NotFoundException(APPARTEMENT_NOT_FOUND + id));
        appartement.setReferenceAppartement(appartementDto.getReferenceAppartement());
        appartement.setNumberChambre(appartementDto.getNumberChambre());
        appartement.setSurface(appartementDto.getSurface());
        appartement.setPrixLocation(appartementDto.getPrixLocation());
        appartement.setPrixVente(appartementDto.getPrixVente());
        appartement.setEtatAppartement(appartementDto.getEtatAppartement());
        appartement.setStatusAppartement(appartementDto.getStatusAppartement());
        appartement.setImmeubleId(appartementDto.getImmeubleId());

        Appartement appartementUpdated = iAppartementRepository.save(appartement);
        updateStatusImmeuble(appartementUpdated.getImmeubleId());

        return modelMapper.map(appartementUpdated, AppartementDto.class);
    }

    @Override
    public List<AppartementDto> all()
    {
        List<Appartement> appartements = iAppartementRepository.findAll();
        return appartements
                .stream()
                .map((appartement) -> modelMapper.map(appartement, AppartementDto.class))
                .toList();
    }

    @Override
    public AppartementDto byId(Long id)
    {
        Appartement appartement = iAppartementRepository.findById(id).orElseThrow(() -> new NotFoundException(APPARTEMENT_NOT_FOUND + id));
        return modelMapper.map(appartement, AppartementDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iAppartementRepository.deleteById(id);
    }

    @Override
    public List<AppartementDto> byIdAndImmeuble(Long immeubleId)
    {
        List<Appartement> appartements = iAppartementRepository.findByImmeubleId(immeubleId);
        return appartements
                .stream()
                .map((appartement) -> modelMapper.map(appartement, AppartementDto.class))
                .toList();
    }
}
