package com.example.agenceservice.client;

import com.example.agenceservice.dto.MediaDto;
import com.example.agenceservice.entity.enums.MediaStatus;
import com.example.agenceservice.exception.MediaClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class MediaClientFallBack implements MediaClient{

    @Override
    public ResponseEntity<List<MediaDto>> save(List<MultipartFile> files, Long relatedId, MediaStatus mediaStatus) {
        throw  new MediaClientException("Erreur in save media client methode");
    }

    @Override
    public ResponseEntity<List<MediaDto>> update(List<MultipartFile> files, Long relatedId, MediaStatus mediaStatus) {
        throw  new MediaClientException("Erreur in update media client methode");
    }

    @Override
    public void deleteMediaByRelatedId(Long relatedId, MediaStatus mediaStatus) {
        throw  new MediaClientException("Erreur in delete media client methode");

    }

    @Override
    public ResponseEntity<List<MediaDto>> getMediaByRelatedId(Long relatedId, MediaStatus mediaStatus) {
        throw  new MediaClientException("Erreur in get media by id client methode");
    }
}
