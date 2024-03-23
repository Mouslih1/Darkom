package com.example.paymentcontratservice.repositories;

import com.example.paymentcontratservice.entities.PaymentContratLoyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentContratLoyerRepository extends JpaRepository<PaymentContratLoyer, Long> {
}
