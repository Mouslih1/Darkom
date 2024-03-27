package com.media.service.service;

import com.media.service.dto.MediaDto;
import com.media.service.exception.MediaException;
import com.media.service.model.Media;
import com.media.service.model.enums.MediaStatus;
import com.media.service.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final FileStorageService fileStorageService;
    private final MediaRepository mediaRepository;
    private final ModelMapper modelMapper;


    public MediaDto upload(MultipartFile file, Long relatedId, MediaStatus mediaStatus) throws IOException
    {
        MediaDto metadata = fileStorageService.store(file, relatedId, mediaStatus);
        Media media = mediaRepository.save(modelMapper.map(metadata, Media.class));
        return modelMapper.map(media, MediaDto.class);
    }

    public MediaDto update(MultipartFile file, Long relatedId, MediaStatus mediaStatus) throws IOException
    {
        System.out.println(file);

        List<Media> medias = mediaRepository.findByRelatedIdAndMediaStatus(relatedId, mediaStatus);
        if(!medias.isEmpty())
        {
            medias.forEach(media -> {
                fileStorageService.delete(media.getFilename());
                 mediaRepository.delete(media);
            });
        }


        MediaDto metadata = fileStorageService.store(file, relatedId, mediaStatus);
        Media media = mediaRepository.save(modelMapper.map(metadata, Media.class));
        return modelMapper.map(media, MediaDto.class);
    }

    public void delete(String mediaUuid)
    {
        Media media = mediaRepository.findByMediaUuid(mediaUuid)
                .orElseThrow(() -> new MediaException("Media not found with uuid: " + mediaUuid));
        fileStorageService.delete(media.getFilename());
        mediaRepository.delete(media);
    }

    public List<MediaDto> getMediaByRelatedId(Long relatedId, MediaStatus mediaStatus)
    {
        List<Media> medias = mediaRepository.findByRelatedIdAndMediaStatus(relatedId, mediaStatus);
        return medias
                .stream()
                .map((media) -> modelMapper.map(media, MediaDto.class))
                .toList();
    }

    public void deleteMediaByRelatedId(Long relatedId, MediaStatus mediaStatus)
    {
        mediaRepository.findByRelatedIdAndMediaStatus(relatedId, mediaStatus).forEach(media -> {
            fileStorageService.delete(media.getFilename());
            mediaRepository.delete(media);
        });
    }
}
