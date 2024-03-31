package com.example.appartementservice.service.impl;

import com.example.appartementservice.client.ImmeubleClient;
import com.example.appartementservice.dto.AppartementDto;
import com.example.appartementservice.dto.ImmeubleDto;
import com.example.appartementservice.entity.Appartement;
import com.example.appartementservice.entity.enums.EtatAppartement;
import com.example.appartementservice.entity.enums.StatusImmeuble;
import com.example.appartementservice.exception.NotFoundException;
import com.example.appartementservice.exception.ValidationException;
import com.example.appartementservice.repository.IAppartementRepository;
import com.example.appartementservice.service.IAppartementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final static  String APPARTEMENT_OR_AGENCE_NOT_FOUND = "Appartement or agence not found with this id : ";


    @Override
    public AppartementDto save(Long agenceId, AppartementDto appartementDto)
    {
        validation(appartementDto.getImmeubleId());
        appartementDto.setAgenceId(agenceId);
        Appartement appartementSaved = iAppartementRepository.save(modelMapper.map(appartementDto, Appartement.class));

        updateStatusImmeuble(appartementSaved.getImmeubleId());
        
        return modelMapper.map(appartementSaved, AppartementDto.class);
    }

    @Override
    public void validation(Long immeubleId)
    {
        ImmeubleDto immeubleDto = immeubleClient.byId(immeubleId).getBody();

        if(immeubleDto != null && immeubleDto.getNumberApparetement() <= countOfAppartementInImmeuble(immeubleId))
        {
            throw new ValidationException("This immeuble can get juste " + immeubleDto.getNumberApparetement() + " appartement");
        }
    }

    @Override
    public int countOfAppartementInImmeuble(Long immeubleId)
    {
        List<Appartement> appartements = iAppartementRepository.findByImmeubleId(immeubleId);
        return appartements.size();
    }

    @Override
    public void updateEtatAppartementToOccuper(Long id)
    {
        Appartement appartement = iAppartementRepository.findById(id).orElseThrow(() -> new NotFoundException(APPARTEMENT_NOT_FOUND + id));

        appartement.setEtatAppartement(EtatAppartement.OCCUPER);
        iAppartementRepository.save(appartement);
    }

    @Override
    public void updateStatusImmeuble(Long immeubleId)
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

    @Override
    public boolean areAnyApartmentsFree(Long immeubleId)
    {
        List<Appartement> appartements = iAppartementRepository.findByImmeubleId(immeubleId);
        if (!appartements.isEmpty())
        {
            for (Appartement appartement : appartements)
            {
                if (appartement.getEtatAppartement() != null && appartement.getEtatAppartement().equals(EtatAppartement.LIBRE))
                {
                    return true;
                }
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
        appartement.setStatusAppartement(appartementDto.getStatusAppartement());
        appartement.setImmeubleId(appartementDto.getImmeubleId());
        appartement.setEtatAppartement(appartementDto.getEtatAppartement());

        Appartement appartementUpdated = iAppartementRepository.save(appartement);

        updateStatusImmeuble(appartementUpdated.getImmeubleId());

        return modelMapper.map(appartementUpdated, AppartementDto.class);
    }

    @Override
    public List<AppartementDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Appartement> appartements = iAppartementRepository.findAll(pageable);
        return appartements
                .stream()
                .map((appartement) -> modelMapper.map(appartement, AppartementDto.class))
                .toList();
    }

    @Override
    public List<AppartementDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<Appartement> appartements = iAppartementRepository.findByAgenceId(agenceId);

        List<Appartement> paginatedAppartements = appartements.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedAppartements
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
    public AppartementDto byIdAndAgence(Long id, Long agenceId)
    {
        Appartement appartement = iAppartementRepository.findByIdAndAgenceId(id, agenceId)
                .orElseThrow(() -> new NotFoundException(APPARTEMENT_OR_AGENCE_NOT_FOUND + id + "agence : " + agenceId));
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
