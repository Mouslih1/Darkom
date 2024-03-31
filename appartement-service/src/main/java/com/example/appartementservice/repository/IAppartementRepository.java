package com.example.appartementservice.repository;

import com.example.appartementservice.entity.Appartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAppartementRepository extends JpaRepository<Appartement, Long> {

    List<Appartement> findByImmeubleId(Long immeubleId);
    List<Appartement> findByAgenceId(Long AgenceId);
    Optional<Appartement> findByIdAndAgenceId(Long appartementId, Long agenceId);
}
