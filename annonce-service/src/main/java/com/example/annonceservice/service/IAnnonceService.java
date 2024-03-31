package com.example.annonceservice.service;

import com.example.annonceservice.dto.AnnonceRequest;
import com.example.annonceservice.dto.AnnonceRequestPhoto;
import com.example.annonceservice.dto.AnnonceResponse;

import java.util.List;

public interface IAnnonceService {

    AnnonceResponse save(Long agenceId,AnnonceRequest annonceRequest);
    AnnonceResponse byId(Long id);
    AnnonceResponse byIdAndAgence(Long annonceId, Long agenceId);
    List<AnnonceResponse> all(int pageNo, int pageSize);
    List<AnnonceResponse> allByAgence(int pageNo, int pageSize, Long agenceId);
    AnnonceResponse update(Long id,  AnnonceRequest annonceRequest);
    AnnonceResponse updateAnnoncePhoto(Long id, AnnonceRequestPhoto annonceRequestPhoto);
    void delete(Long id);
}
