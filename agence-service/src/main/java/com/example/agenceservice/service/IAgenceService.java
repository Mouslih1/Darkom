package com.example.agenceservice.service;

import com.example.agenceservice.dto.AgenceDto;
import com.example.agenceservice.dto.AgenceLogoRequest;
import com.example.agenceservice.dto.AgenceRequest;
import com.example.agenceservice.dto.AgenceResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAgenceService {

    AgenceResponse save(AgenceRequest agenceRequest);
    AgenceResponse update(Long id, AgenceRequest agenceRequest);
    AgenceResponse updateLogo(Long id, AgenceLogoRequest agenceLogoRequest);
    List<AgenceResponse> all(int pageNo, int pageSize);
    AgenceResponse byId(Long id);
    void delete(Long id);
}
