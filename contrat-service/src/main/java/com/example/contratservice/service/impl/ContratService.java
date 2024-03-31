package com.example.contratservice.service.impl;

import com.example.contratservice.client.AppartementClient;
import com.example.contratservice.client.ImmeubleClient;
import com.example.contratservice.dto.AppartementDto;
import com.example.contratservice.dto.ContratDto;
import com.example.contratservice.dto.ImmeubleDto;
import com.example.contratservice.entity.Contrat;
import com.example.contratservice.entity.enums.EtatAppartement;
import com.example.contratservice.entity.enums.StatusAppartement;
import com.example.contratservice.entity.enums.StatusImmeuble;
import com.example.contratservice.entity.enums.TypeContrat;
import com.example.contratservice.exception.NotFoundException;
import com.example.contratservice.exception.ValidationException;
import com.example.contratservice.repository.IContratRepository;
import com.example.contratservice.service.IContratService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratService implements IContratService {

    private final IContratRepository iContratRepository;
    private final ModelMapper modelMapper;
    private final AppartementClient appartementClient;
    private final ImmeubleClient immeubleClient;
    private static final String CONTRAT_NOT_FOUND = "Contrat not found with this id : ";
    private static final String CONTRAT_OR_AGENCE_NOT_FOUND = "Contrat OR agence not found with this id : ";


    @Override
    public ContratDto save(Long agenceId,ContratDto contratDto)
    {
        AppartementDto appartementDto = appartementClient.byId(contratDto.getAppartementId()).getBody();
        assert appartementDto != null;

        ImmeubleDto immeubleDto = immeubleClient.byId(appartementDto.getImmeubleId()).getBody();
        assert immeubleDto != null;

        contratDto.setAgenceId(agenceId);
        appartementClient.updateEtatAppartementToOccuper(appartementDto.getId());

        getTypeOfContratAndMontantOfContrat(appartementDto, contratDto);

        updateStatusImmeuble(immeubleDto.getId());

        validation(appartementDto.getId());

        Contrat contrat = iContratRepository.save(modelMapper.map(contratDto, Contrat.class));
        return modelMapper.map(contrat, ContratDto.class);
    }

    @Override
    public void getTypeOfContratAndMontantOfContrat(AppartementDto appartementDto, ContratDto contratDto)
    {
        if(appartementDto.getStatusAppartement().equals(StatusAppartement.VENTE))
        {
            contratDto.setTypeContrat(TypeContrat.CONTRAT_VENTE);
            contratDto.setMontant(appartementDto.getPrixVente());
        } else if (appartementDto.getStatusAppartement().equals(StatusAppartement.LOUER))
        {
            contratDto.setTypeContrat(TypeContrat.CONTRAT_LOUER);
            contratDto.setMontant(appartementDto.getPrixLocation());
        }
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

        List<AppartementDto> appartements = appartementClient.byIdAndImmeuble(immeubleId).getBody();
        assert appartements != null;
        if (!appartements.isEmpty())
        {
            for (AppartementDto appartement : appartements)
            {
                if (appartement.getEtatAppartement() != null && appartement.getEtatAppartement().equals(EtatAppartement.LIBRE)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean existAppartement(Long appartementId)
    {
        return iContratRepository.existsByAppartementId(appartementId);
    }

    public void validation(Long appartementId)
    {
        if(existAppartement(appartementId))
        {
            throw new ValidationException("We has this appartement in other contrat.");
        }
    }
    @Override
    public ContratDto byId(Long id)
    {
        Contrat contrat = iContratRepository.findById(id).orElseThrow(() -> new NotFoundException(CONTRAT_NOT_FOUND + id));
        return modelMapper.map(contrat, ContratDto.class);
    }

    @Override
    public List<ContratDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Contrat> contrats = iContratRepository.findAll(pageable);

        return contrats
                .stream()
                .map((contrat) -> modelMapper.map(contrat, ContratDto.class))
                .toList();
    }

    @Override
    public ContratDto byIdAndAgence(Long contratId, Long agenceId)
    {
        Contrat contrat = iContratRepository.findByIdAndAgenceId(contratId, agenceId)
                .orElseThrow(() -> new NotFoundException(CONTRAT_OR_AGENCE_NOT_FOUND + contratId + "agence : " + agenceId));
        return modelMapper.map(contrat, ContratDto.class);
    }

    @Override
    public List<ContratDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<Contrat> contrats = iContratRepository.findByAgenceId(agenceId);

        List<Contrat> paginatedContrats = contrats.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedContrats
                .stream()
                .map((contrat) -> modelMapper.map(contrat, ContratDto.class))
                .toList();
    }

    @Override
    public ContratDto update(Long id, ContratDto contratDto)
    {
        Contrat contrat = iContratRepository.findById(id).orElseThrow(() -> new NotFoundException(CONTRAT_NOT_FOUND + id));

        AppartementDto appartementDto = appartementClient.byId(contratDto.getAppartementId()).getBody();
        assert appartementDto != null;

        ImmeubleDto immeubleDto = immeubleClient.byId(appartementDto.getImmeubleId()).getBody();
        assert immeubleDto != null;

        appartementClient.updateEtatAppartementToOccuper(appartementDto.getId());

        getTypeOfContratAndMontantOfContrat(appartementDto, contratDto);

        updateStatusImmeuble(immeubleDto.getId());

        validation(appartementDto.getId());

        contrat.setAppartementId(contratDto.getAppartementId());
        contrat.setDateSignature(contratDto.getDateSignature());
        contrat.setPropreitaireId(contratDto.getPropreitaireId());
        contrat.setMontant(contratDto.getMontant());
        contrat.setTypeContrat(contratDto.getTypeContrat());

        Contrat contratUpdated = iContratRepository.save(contrat);
        return modelMapper.map(contratUpdated, ContratDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iContratRepository.deleteById(id);
    }
}
