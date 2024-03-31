package com.example.travauxservice.repository;

import com.example.travauxservice.entity.Travaux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITravauxRepository extends JpaRepository<Travaux, Long> {

    List<Travaux> findByAgenceId(Long AgenceId);
    Optional<Travaux> findByIdAndAgenceId(Long evenementId, Long agenceId);
}
