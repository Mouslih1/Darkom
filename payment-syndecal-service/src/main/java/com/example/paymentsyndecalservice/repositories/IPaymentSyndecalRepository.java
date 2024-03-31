package com.example.paymentsyndecalservice.repositories;

import com.example.paymentsyndecalservice.entities.PaymentSyndecal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentSyndecalRepository extends JpaRepository<PaymentSyndecal, Long> {

    List<PaymentSyndecal> findByAgenceId(Long AgenceId);
    Optional<PaymentSyndecal> findByIdAndAgenceId(Long evenementId, Long agenceId);
}
