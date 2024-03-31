package com.example.plainte.repository;

import com.example.plainte.entity.Plainte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlainteRepository extends JpaRepository<Plainte, Long> {
    List<Plainte> findByAgenceId(Long AgenceId);
    Optional<Plainte> findByIdAndAgenceId(Long evenementId, Long agenceId);
}
