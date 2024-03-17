package com.example.plainte.repository;

import com.example.plainte.entity.Plainte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlainteRepository extends JpaRepository<Plainte, Long> {
}
