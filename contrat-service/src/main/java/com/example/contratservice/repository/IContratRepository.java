package com.example.contratservice.repository;

import com.example.contratservice.entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContratRepository extends JpaRepository<Contrat, Long> {

    boolean existsByAppartementId(Long appartementId);
}
