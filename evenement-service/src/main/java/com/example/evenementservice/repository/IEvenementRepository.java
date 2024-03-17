package com.example.evenementservice.repository;

import com.example.evenementservice.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEvenementRepository extends JpaRepository<Evenement, Long> {
}
