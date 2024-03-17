package com.example.travauxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TravauxServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravauxServiceApplication.class, args);
    }

}
