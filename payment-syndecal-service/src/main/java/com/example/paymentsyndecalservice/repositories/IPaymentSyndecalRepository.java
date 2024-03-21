package com.example.paymentsyndecalservice.repositories;

import com.example.paymentsyndecalservice.entities.PaymentSyndecal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentSyndecalRepository extends JpaRepository<PaymentSyndecal, Long> {
}
