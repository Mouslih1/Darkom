package com.example.annonceservice.service.impl;

import com.example.annonceservice.client.AppartementClient;
import com.example.annonceservice.client.MediaClient;
import com.example.annonceservice.dto.*;
import com.example.annonceservice.entity.Annonce;
import com.example.annonceservice.entity.enums.MediaStatus;
import com.example.annonceservice.entity.enums.StatusAppartement;
import com.example.annonceservice.entity.enums.TypeAnnonce;
import com.example.annonceservice.exception.NotFoundException;
import com.example.annonceservice.repository.IAnnonceRepository;
import com.example.annonceservice.service.IAnnonceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnonceService implements IAnnonceService {

    private final IAnnonceRepository iAnnonceRepository;
    private final ModelMapper modelMapper;
    private final AppartementClient appartementClient;
    private final MediaClient mediaClient;
    private static final String ANNONCE_NOT_FOUND = "Annonce not found this id : ";
    private static final String ANNONCE_OR_AGENCE_NOT_FOUND = "Annonce not found this id : ";

    @Override
    public AnnonceResponse save(Long agenceId, AnnonceRequest annonceRequest)
    {
        getTypeAnnonceAndPrixByStatusAppartement(annonceRequest);
        annonceRequest.setAgenceId(agenceId);
        Annonce annonce = iAnnonceRepository.save(modelMapper.map(annonceRequest, Annonce.class));
        AnnonceResponse annonceResponse = new AnnonceResponse();
        annonceResponse.setAnnonceDto(modelMapper.map(annonce, AnnonceDto.class));

        if(annonceRequest.getMultipartFiles() != null && !annonceRequest.getMultipartFiles().isEmpty())
        {
            annonceResponse.setMedias(mediaClient.save(annonceRequest.getMultipartFiles(),
                    annonce.getId(), MediaStatus.ANNONCE_PHOTO).getBody());
        }

        return annonceResponse;
    }

    public void getTypeAnnonceAndPrixByStatusAppartement(AnnonceRequest annonceRequest)
    {
        AppartementDto appartementDto = appartementClient.byId(annonceRequest.getAppartementId()).getBody();
        assert appartementDto != null;
        if(appartementDto.getStatusAppartement().equals(StatusAppartement.VENTE))
        {
            annonceRequest.setTypeAnnonce(TypeAnnonce.A_VENDRE);
            annonceRequest.setPrixVente(appartementDto.getPrixVente());
        } else {
            annonceRequest.setTypeAnnonce(TypeAnnonce.A_LOUER);
            annonceRequest.setPrixLouer(appartementDto.getPrixLocation());
        }
    }

    @Override
    public AnnonceResponse byId(Long id)
    {
        Annonce annonce = iAnnonceRepository.findById(id).orElseThrow(() -> new NotFoundException(ANNONCE_NOT_FOUND + id));
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(annonce.getId(), MediaStatus.ANNONCE_PHOTO).getBody();

        return new AnnonceResponse(modelMapper.map(annonce, AnnonceDto.class), medias);
    }

    @Override
    public AnnonceResponse byIdAndAgence(Long annonceId, Long agenceId)
    {
        Annonce annonce = iAnnonceRepository.findByIdAndAgenceId(annonceId, agenceId)
                .orElseThrow(() -> new NotFoundException(ANNONCE_OR_AGENCE_NOT_FOUND + annonceId + "agence : " + agenceId));
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(annonce.getId(), MediaStatus.PHOTO_PROFIL).getBody();
        return new AnnonceResponse(modelMapper.map(annonce, AnnonceDto.class), medias);
    }

    @Override
    public List<AnnonceResponse> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Annonce> annonces = iAnnonceRepository.findAll(pageable);

        return annonces
                .stream()
                .map((annonce) -> {
                    AnnonceDto annonceDto = modelMapper.map(annonce, AnnonceDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(annonceDto.getId(), MediaStatus.ANNONCE_PHOTO).getBody();
                    return new AnnonceResponse(annonceDto, medias);
                })
                .toList();
    }

    @Override
    public List<AnnonceResponse> allByAgence(int pageNo, int pageSize, Long agenceId)
    {
        int startIndex = (pageNo) * pageSize;
        List<Annonce> annonces = iAnnonceRepository.findByAgenceId(agenceId);

        List<Annonce> paginatedAnnonces = annonces.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedAnnonces
                .stream()
                .map((annonce) -> {
                    AnnonceDto annonceDto = modelMapper.map(annonce, AnnonceDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(annonceDto.getId(), MediaStatus.ANNONCE_PHOTO).getBody();
                    return new AnnonceResponse(annonceDto, medias);
                })
                .toList();
    }

    @Override
    public AnnonceResponse update(Long id, AnnonceRequest annonceRequest)
    {
        getTypeAnnonceAndPrixByStatusAppartement(annonceRequest);

        Annonce annonce = iAnnonceRepository.findById(id).orElseThrow(() -> new NotFoundException(ANNONCE_NOT_FOUND + id));
        annonce.setStatusAnnonce(annonceRequest.getStatusAnnonce());
        annonce.setDescription(annonceRequest.getDescription());
        annonce.setTitre(annonceRequest.getTitre());
        annonce.setAppartementId(annonceRequest.getAppartementId());
        annonce.setTypeAnnonce(annonceRequest.getTypeAnnonce());
        annonce.setPrixLouer(annonceRequest.getPrixLouer());
        annonce.setPrixVente(annonceRequest.getPrixVente());

        Annonce annonceUpdated = iAnnonceRepository.save(annonce);
        List<MediaDto> mediaDto = mediaClient.getMediaByRelatedId(annonceUpdated.getId(), MediaStatus.ANNONCE_PHOTO).getBody();

        return new AnnonceResponse(modelMapper.map(annonceUpdated, AnnonceDto.class), mediaDto);
    }

    @Override
    public AnnonceResponse updateAnnoncePhoto(Long id, AnnonceRequestPhoto annonceRequestPhoto)
    {
        Annonce annonce = iAnnonceRepository.findById(id).orElseThrow(() -> new NotFoundException(ANNONCE_NOT_FOUND + id));
        List<MediaDto> mediaDto = new ArrayList<>();

        if(annonceRequestPhoto.getMultipartFiles() != null && !annonceRequestPhoto.getMultipartFiles().isEmpty())
        {
            mediaDto = mediaClient.update(annonceRequestPhoto.getMultipartFiles(),
                    annonce.getId(), MediaStatus.ANNONCE_PHOTO).getBody();
        }

        return new AnnonceResponse(modelMapper.map(annonce, AnnonceDto.class), mediaDto);
    }

    @Override
    public void delete(Long id)
    {
        iAnnonceRepository.deleteById(id);
        mediaClient.deleteMediaByRelatedId(id, MediaStatus.ANNONCE_PHOTO);
    }
}
