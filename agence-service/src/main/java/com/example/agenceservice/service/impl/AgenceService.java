package com.example.agenceservice.service.impl;

import com.example.agenceservice.dto.*;
import com.example.agenceservice.entity.Agence;
import com.example.agenceservice.entity.enums.MediaStatus;
import com.example.agenceservice.exception.NotFoundException;
import com.example.agenceservice.client.MediaClient;
import com.example.agenceservice.repository.IAgenceRepository;
import com.example.agenceservice.service.IAgenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AgenceService implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;
    private static final String AGENCE_NOT_FOUND = "Agence not found with this id : ";

    @Override
    public AgenceResponse save(AgenceRequest agenceRequest)
    {
        Agence agence = agenceRepository.save(modelMapper.map(agenceRequest, Agence.class));
        AgenceResponse agenceResponse = new AgenceResponse();
        agenceResponse.setAgenceDto(modelMapper.map(agence, AgenceDto.class));
        if(agenceRequest.getMultipartFiles() != null && !agenceRequest.getMultipartFiles().isEmpty())
        {
            List<MediaDto> mediaDto = mediaClient.save(agenceRequest.getMultipartFiles(),
                    agence.getAgentCreatedBy(), agence.getId(), MediaStatus.LOGO_AGENCE).getBody();
            agenceResponse.setMedias(mediaDto);
        }
        return agenceResponse;
    }

    @Override
    public AgenceResponse update(Long id, AgenceRequest agenceRequest)
    {
        Agence agence = agenceRepository.findById(id).orElseThrow(() -> new NotFoundException(AGENCE_NOT_FOUND + id));

        agence.setUpdatedAt(LocalDateTime.now());
        agence.setEmail(agenceRequest.getEmail());
        agence.setAddress(agenceRequest.getAddress());
        agence.setTelephone(agenceRequest.getTelephone());
        agence.setName(agenceRequest.getName());
        agence.setVille(agenceRequest.getVille());

        Agence agenceUpdated = agenceRepository.save(agence);
        List<MediaDto> mediaDto = mediaClient.getMediaByRelatedId(agence.getId(), MediaStatus.LOGO_AGENCE).getBody();

        return new AgenceResponse(modelMapper.map(agenceUpdated, AgenceDto.class), mediaDto);
    }

    @Override
    public AgenceResponse updateLogo(Long id, AgenceLogoRequest agenceLogoRequest)
    {
        Agence agence = agenceRepository.findById(id).orElseThrow(() -> new NotFoundException(AGENCE_NOT_FOUND + id));
        List<MediaDto> mediaDto = new ArrayList<>();
        if(agenceLogoRequest.getMultipartFiles() != null && !agenceLogoRequest.getMultipartFiles().isEmpty())
        {
            System.out.println(agenceLogoRequest.getMultipartFiles());
            System.out.println(agenceLogoRequest.getAgentUpdatedBy());
            mediaDto = mediaClient.update(agenceLogoRequest.getMultipartFiles(),
                    agenceLogoRequest.getAgentUpdatedBy(), agence.getId(), MediaStatus.LOGO_AGENCE).getBody();
        }

        return new AgenceResponse(modelMapper.map(agence, AgenceDto.class), mediaDto);
    }


    @Override
    public List<AgenceResponse> all()
    {
        List<Agence> agences = agenceRepository.findAll();

        return agences.stream()
                .map(agence -> {
                    AgenceDto agenceDto = modelMapper.map(agence, AgenceDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(agence.getId(), MediaStatus.LOGO_AGENCE).getBody();
                    return new AgenceResponse(agenceDto, medias);
                })
                .collect(Collectors.toList());
    }

    @Override
    public AgenceResponse byId(Long id)
    {
        Agence agence = agenceRepository.findById(id).orElseThrow(() -> new NotFoundException(AGENCE_NOT_FOUND + id));
        AgenceResponse agenceResponse = new AgenceResponse();
        agenceResponse.setAgenceDto(modelMapper.map(agence, AgenceDto.class));
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(id, MediaStatus.LOGO_AGENCE).getBody();
        agenceResponse.setMedias(medias);

        return agenceResponse;
    }

    @Override
    public void delete(Long id)
    {
        agenceRepository.deleteById(id);
        mediaClient.deleteMediaByRelatedId(id, MediaStatus.LOGO_AGENCE);
    }
}
