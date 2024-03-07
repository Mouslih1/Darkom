package com.example.agenceservice.service.impl;

import com.example.agenceservice.dto.AgenceDto;
import com.example.agenceservice.dto.AgenceRequest;
import com.example.agenceservice.dto.AgenceResponse;
import com.example.agenceservice.dto.MediaDto;
import com.example.agenceservice.entity.Agence;
import com.example.agenceservice.entity.enums.MediaStatus;
import com.example.agenceservice.exception.NotFoundException;
import com.example.agenceservice.client.MediaClient;
import com.example.agenceservice.repository.IAgenceRepository;
import com.example.agenceservice.service.IAgenceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AgenceService implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;
    private static final String AGENCE_NOT_FOUND = "Agence not found with this id : ";

    @Override
    public AgenceResponse save(Long agentCreatedBy, AgenceRequest agenceRequest)
    {
        agenceRequest.setAgentCreatedBy(agentCreatedBy);
        agenceRequest.setCreatedAt(LocalDateTime.now());

        Agence agence = agenceRepository.save(modelMapper.map(agenceRequest, Agence.class));
        AgenceResponse agenceResponse = new AgenceResponse();
        agenceResponse.setAgenceDto(modelMapper.map(agence, AgenceDto.class));
        if(agenceRequest.getMultipartFiles() != null && !agenceRequest.getMultipartFiles().isEmpty())
        {
            List<MediaDto> mediaDto = mediaClient.save(agenceRequest.getMultipartFiles(),
                    agentCreatedBy, agence.getId(), MediaStatus.LOGO_AGENCE).getBody();
            agenceResponse.setMedias(mediaDto);
        }
        return agenceResponse;
    }

    @Override
    public AgenceDto update(Long id, AgenceDto agenceDto)
    {
        Agence agence = agenceRepository.findById(id).orElseThrow(() -> new NotFoundException(AGENCE_NOT_FOUND + id));

        agence.setUpdatedAt(LocalDateTime.now());
        agence.setEmail(agenceDto.getEmail());
        agence.setAddress(agenceDto.getAddress());
        agence.setTelephone(agenceDto.getTelephone());
        agence.setName(agenceDto.getName());
        agence.setVille(agenceDto.getVille());

        Agence agenceUpdated = agenceRepository.save(agence);
        return modelMapper.map(agenceUpdated, AgenceDto.class);
    }

    @Override
    public List<AgenceResponse> all()
    {
        List<Agence> agences = agenceRepository.findAll();

        return agences.stream()
                .map(agence -> {
                    AgenceDto agenceDto = modelMapper.map(agence, AgenceDto.class);
                    List<MediaDto> medias = mediaClient.getMediaByRelatedId(agence.getId()).getBody();
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
        List<MediaDto> medias = mediaClient.getMediaByRelatedId(id).getBody();
        agenceResponse.setMedias(medias);

        return agenceResponse;
    }

    @Override
    public void delete(Long id)
    {
        agenceRepository.deleteById(id);
        mediaClient.deleteMediaByRelatedId(id);
    }
}
