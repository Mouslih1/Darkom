package com.example.immeubleservice.repository;

import com.example.immeubleservice.entity.Immeuble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IimeubleRepository extends JpaRepository<Immeuble, Long> {
}
