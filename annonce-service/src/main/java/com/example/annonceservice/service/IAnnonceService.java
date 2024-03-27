package com.example.annonceservice.service;

import com.example.annonceservice.dto.AnnonceRequest;
import com.example.annonceservice.dto.AnnonceRequestPhoto;
import com.example.annonceservice.dto.AnnonceResponse;

import java.util.List;

public interface IAnnonceService {

    AnnonceResponse save(AnnonceRequest annonceRequest);
    AnnonceResponse byId(Long id);
    List<AnnonceResponse> all(int pageNo, int pageSize);
    AnnonceResponse update(Long id,  AnnonceRequest annonceRequest);
    AnnonceResponse updateAnnoncePhoto(Long id, AnnonceRequestPhoto annonceRequestPhoto);
    void delete(Long id);
}
