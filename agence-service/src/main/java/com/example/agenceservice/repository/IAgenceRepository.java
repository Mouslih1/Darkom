package com.example.agenceservice.repository;

import com.example.agenceservice.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgenceRepository extends JpaRepository<Agence, Long> {
}
