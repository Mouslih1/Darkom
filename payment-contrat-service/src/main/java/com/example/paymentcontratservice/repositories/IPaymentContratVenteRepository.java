package com.example.paymentcontratservice.repositories;

import com.example.paymentcontratservice.entities.PaymentContratLoyer;
import com.example.paymentcontratservice.entities.PaymentContratVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentContratVenteRepository extends JpaRepository<PaymentContratVente, Long> {
    List<PaymentContratVente> findByAgenceId(Long AgenceId);
    Optional<PaymentContratVente> findByIdAndAgenceId(Long evenementId, Long agenceId);
}
