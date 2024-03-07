package com.example.agenceservice.client;

import com.example.agenceservice.dto.MediaDto;
import com.example.agenceservice.entity.enums.MediaStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(url = "http://localhost:8016/api/v1/medias",name = "MEDIA-SERVICE")
public interface MediaClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<MediaDto>> save(@RequestPart("files") List<MultipartFile> files,
                                        @RequestParam("agentCreatedBy") Long agentCreatedBy,
                                        @RequestParam("relatedId") Long relatedId,
                                        @RequestParam("mediaStatus") MediaStatus mediaStatus
    );


    @DeleteMapping("/related/{relatedId}")
    void deleteMediaByRelatedId(@PathVariable("relatedId") Long relatedId);

    @GetMapping("/related/{relatedId}")
    ResponseEntity<List<MediaDto>> getMediaByRelatedId(
            @PathVariable("relatedId") Long relatedId
    );

}
