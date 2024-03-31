package com.example.immeubleservice.repository;

import com.example.immeubleservice.entity.Immeuble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IimeubleRepository extends JpaRepository<Immeuble, Long> {
    List<Immeuble> findByAgenceId(Long AgenceId);
    Optional<Immeuble> findByIdAndAgenceId(Long evenementId, Long agenceId);
}
