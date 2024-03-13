package com.example.travauxservice.repository;

import com.example.travauxservice.entity.Travaux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITravauxRepository extends JpaRepository<Travaux, Long> {
}
