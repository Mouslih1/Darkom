package com.media.service.repository;

import com.media.service.model.Media;
import com.media.service.model.enums.MediaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findByMediaUuid(String mediaUuid);
    List<Media> findByRelatedIdAndMediaStatus(Long relatedId, MediaStatus mediaStatus);
}
