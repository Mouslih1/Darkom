package com.example.paymentcontratservice.clients;

import com.example.paymentcontratservice.dtos.ContratDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9001/api/v1/contrats", name = "contrats")
public interface ContratClient {

    @GetMapping("/{id}")
    ResponseEntity<ContratDto> byId(@PathVariable Long id);
}
