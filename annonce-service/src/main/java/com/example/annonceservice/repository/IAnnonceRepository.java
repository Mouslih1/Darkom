package com.example.annonceservice.repository;

import com.example.annonceservice.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByAgenceId(Long agenceId);
    Optional<Annonce> findByIdAndAgenceId(Long userId, Long agenceId);
}
