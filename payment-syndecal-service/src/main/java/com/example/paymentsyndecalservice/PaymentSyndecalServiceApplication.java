package com.example.paymentsyndecalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PaymentSyndecalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentSyndecalServiceApplication.class, args);
    }

}
