package com.media.service.service;

import com.media.service.dto.MediaDto;
import com.media.service.exception.InvalidFileException;
import com.media.service.exception.InvalidFileNameException;
import com.media.service.exception.StorageException;
import com.media.service.model.enums.MediaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    @Value("/home/maro/IdeaProjects/Darkom/images/")
    private String uploadDirectory;

    public MediaDto store(MultipartFile file, Long agencyCreatedBy, Long relatedId, MediaStatus mediaType) throws IOException
    {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (file.isEmpty())
        {
            throw new InvalidFileException("Failed to store empty file " + filename);
        }

        if (filename.contains(".."))
        {
            throw new InvalidFileNameException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }

        String newFilename = mediaType + "-" + UUID.randomUUID().toString().substring(0,5) + "-" + filename;
        String encodedFilename = URLEncoder.encode(newFilename, StandardCharsets.UTF_8).replace("+", "%20");
        try (InputStream inputStream = file.getInputStream())
        {
            Path userDir = Paths.get(uploadDirectory);

            if(Files.notExists(userDir)) {
                Files.createDirectory(userDir);
            }

            Files.copy(inputStream, userDir.resolve(newFilename),
                    StandardCopyOption.REPLACE_EXISTING);
        }

        String fileUrl = String.format("http://localhost:8222/api/v1/medias/images/" + encodedFilename);

        return MediaDto.builder()
                .filename(newFilename)
                .agentCreatedBy(agencyCreatedBy)
                .mediaUuid(relatedId + "-" + agencyCreatedBy + "-" + UUID.randomUUID().toString().substring(0,4))
                .relatedId(relatedId)
                .mediaStatus(MediaStatus.valueOf(mediaType.toString()))
                .fileType(file.getContentType())
                .size(file.getSize())
                .uri(fileUrl)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public void delete(String filename)
    {
        try {
            Path filePath = Paths.get(uploadDirectory, filename);
            Files.delete(filePath);
        } catch (IOException e) {
            throw new StorageException("Failed to delete file " + filename, e);
        }
    }
}
