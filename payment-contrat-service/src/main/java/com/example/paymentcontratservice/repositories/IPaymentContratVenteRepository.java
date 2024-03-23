package com.example.paymentcontratservice.repositories;

import com.example.paymentcontratservice.entities.PaymentContratVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentContratVenteRepository extends JpaRepository<PaymentContratVente, Long> {
}
