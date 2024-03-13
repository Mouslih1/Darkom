package com.example.appartementservice.repository;

import com.example.appartementservice.entity.Appartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppartementRepository extends JpaRepository<Appartement, Long> {

    List<Appartement> findByImmeubleId(Long immeubleId);
}
