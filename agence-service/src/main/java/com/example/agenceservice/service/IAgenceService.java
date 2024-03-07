package com.example.agenceservice.service;

import com.example.agenceservice.dto.AgenceDto;
import com.example.agenceservice.dto.AgenceRequest;
import com.example.agenceservice.dto.AgenceResponse;

import java.util.List;

public interface IAgenceService {

    AgenceResponse save(Long agentCreatedBy, AgenceRequest agenceRequest);
    AgenceDto update(Long id, AgenceDto agenceDto);
    List<AgenceResponse> all();
    AgenceResponse byId(Long id);
    void delete(Long id);
}
