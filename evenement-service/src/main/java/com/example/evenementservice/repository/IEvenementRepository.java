package com.example.evenementservice.repository;

import com.example.evenementservice.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEvenementRepository extends JpaRepository<Evenement, Long> {
    List<Evenement> findByAgenceId(Long AgenceId);
    Optional<Evenement> findByIdAndAgenceId(Long evenementId, Long agenceId);
}
