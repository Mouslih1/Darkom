package com.example.contratservice.repository;

import com.example.contratservice.entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IContratRepository extends JpaRepository<Contrat, Long> {

    boolean existsByAppartementId(Long appartementId);
    List<Contrat> findByAgenceId(Long AgenceId);
    Optional<Contrat> findByIdAndAgenceId(Long appartementId, Long agenceId);
}
