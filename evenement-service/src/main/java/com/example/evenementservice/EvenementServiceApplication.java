package com.example.evenementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EvenementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvenementServiceApplication.class, args);
    }

}
