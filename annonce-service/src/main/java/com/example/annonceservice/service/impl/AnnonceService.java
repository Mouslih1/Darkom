package com.example.annonceservice.service.impl;

import com.example.annonceservice.client.AppartementClient;
import com.example.annonceservice.dto.AnnonceDto;
import com.example.annonceservice.dto.AppartementDto;
import com.example.annonceservice.entity.Annonce;
import com.example.annonceservice.entity.enums.StatusAppartement;
import com.example.annonceservice.entity.enums.TypeAnnonce;
import com.example.annonceservice.repository.IAnnonceRepository;
import com.example.annonceservice.service.IAnnonceService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnonceService implements IAnnonceService {

    private final IAnnonceRepository iAnnonceRepository;
    private final ModelMapper modelMapper;
    private final AppartementClient appartementClient;
    private static final String ANNONCE_NOT_FOUND = "Annonce not found this id : ";

    @Override
    public AnnonceDto save(AnnonceDto annonceDto)
    {
        getTypeAnnonceAndPrixByStatusAppartement(annonceDto);
        Annonce annonce = iAnnonceRepository.save(modelMapper.map(annonceDto, Annonce.class));
        return modelMapper.map(annonce, AnnonceDto.class);
    }

    public void getTypeAnnonceAndPrixByStatusAppartement(AnnonceDto annonceDto)
    {
        AppartementDto appartementDto = appartementClient.byId(annonceDto.getAppartementId()).getBody();
        assert appartementDto != null;
        if(appartementDto.getStatusAppartement().equals(StatusAppartement.VENTE))
        {
            annonceDto.setTypeAnnonce(TypeAnnonce.A_VENDRE);
            annonceDto.setPrixVente(appartementDto.getPrixVente());
        } else if (appartementDto.getStatusAppartement().equals(StatusAppartement.LOUER))
        {
            annonceDto.setTypeAnnonce(TypeAnnonce.A_LOUER);
            annonceDto.setPrixLouer(appartementDto.getPrixLocation());
        }else {
            annonceDto.setTypeAnnonce(TypeAnnonce.A_VENDRE_OR_A_LOUER);
            annonceDto.setPrixLouer(appartementDto.getPrixLocation());
            annonceDto.setPrixVente(appartementDto.getPrixVente());
        }
    }
    @Override
    public AnnonceDto byId(Long id)
    {
        Annonce annonce = iAnnonceRepository.findById(id).orElseThrow(() -> new NotFoundException(ANNONCE_NOT_FOUND + id));
        return modelMapper.map(annonce, AnnonceDto.class);
    }

    @Override
    public List<AnnonceDto> all()
    {
        List<Annonce> annonces = iAnnonceRepository.findAll();
        return annonces
                .stream()
                .map((element) -> modelMapper.map(element, AnnonceDto.class))
                .toList();
    }

    @Override
    public AnnonceDto update(Long id, AnnonceDto annonceDto)
    {
        getTypeAnnonceAndPrixByStatusAppartement(annonceDto);

        Annonce annonce = iAnnonceRepository.findById(id).orElseThrow(() -> new NotFoundException(ANNONCE_NOT_FOUND + id));
        annonce.setStatusAnnonce(annonceDto.getStatusAnnonce());
        annonce.setDescription(annonceDto.getDescription());
        annonce.setTitre(annonceDto.getTitre());
        annonce.setAppartementId(annonceDto.getAppartementId());
        annonce.setTypeAnnonce(annonceDto.getTypeAnnonce());
        annonce.setPrixLouer(annonceDto.getPrixLouer());
        annonce.setPrixVente(annonceDto.getPrixVente());

        Annonce annonceUpdated = iAnnonceRepository.save(annonce);
        return modelMapper.map(annonceUpdated, AnnonceDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iAnnonceRepository.deleteById(id);
    }
}
