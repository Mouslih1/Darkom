package com.media.service.controller;


import com.media.service.dto.MediaDto;
import com.media.service.exception.Error;
import com.media.service.model.enums.MediaStatus;
import com.media.service.service.ImageService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MediaController {

    private final ImageService mediaService;

    @PostMapping
    public ResponseEntity<List<MediaDto>> save(@RequestParam("files") List<MultipartFile> files,
                                              @RequestParam("relatedId") Long relatedId,
                                              @RequestParam("mediaStatus") MediaStatus mediaStatus
    ) throws IOException
    {
        List<MediaDto> medias = new ArrayList<>();
        for (MultipartFile file : files)
        {
            MediaDto media = mediaService.upload(file,relatedId, mediaStatus);
            medias.add(media);
        }
        return new ResponseEntity<>(medias, HttpStatus.CREATED);
    }

    @PutMapping("relatedId/{relatedId}")
    public ResponseEntity<List<MediaDto>> update(@RequestPart("files") List<MultipartFile> files,
                                               @PathVariable Long relatedId,
                                               @RequestParam("mediaStatus") MediaStatus mediaStatus
    ) throws IOException
    {
        List<MediaDto> medias = new ArrayList<>();
        for (MultipartFile file : files)
        {
            MediaDto media = mediaService.update(file,relatedId, mediaStatus);
            medias.add(media);
        }
        return new ResponseEntity<>(medias, HttpStatus.CREATED);
    }

    @DeleteMapping("/{mediaUuid}")
    public ResponseEntity<Error> delete(@PathVariable String mediaUuid)
    {
        Error error = new Error("Media deleted by successfully.");
        mediaService.delete(mediaUuid);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @GetMapping("/related/{relatedId}")
    public ResponseEntity<List<MediaDto>> getMediaByRelatedId(@PathVariable("relatedId") Long relatedId, MediaStatus mediaStatus)
    {
        return new ResponseEntity<>(mediaService.getMediaByRelatedId(relatedId, mediaStatus), HttpStatus.OK);
    }

    @DeleteMapping("/related/{relatedId}")
    public void deleteMediaByRelatedId(@PathVariable("relatedId") Long relatedId, MediaStatus mediaStatus)
    {
        mediaService.deleteMediaByRelatedId(relatedId, mediaStatus);
    }
}