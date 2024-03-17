package com.example.annonceservice.repository;

import com.example.annonceservice.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnnonceRepository extends JpaRepository<Annonce, Long> {
}
