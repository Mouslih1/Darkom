package com.example.immeubleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImmeubleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImmeubleServiceApplication.class, args);
    }

}
