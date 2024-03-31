package com.example.paymentcontratservice.repositories;

import com.example.paymentcontratservice.entities.PaymentContratLoyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentContratLoyerRepository extends JpaRepository<PaymentContratLoyer, Long> {
    List<PaymentContratLoyer> findByAgenceId(Long AgenceId);
    Optional<PaymentContratLoyer> findByIdAndAgenceId(Long evenementId, Long agenceId);
}
