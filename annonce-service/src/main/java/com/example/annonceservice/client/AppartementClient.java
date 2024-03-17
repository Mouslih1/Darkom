package com.example.annonceservice.client;

import com.example.annonceservice.dto.AppartementDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8087/api/v1/appartements" , name = "appartement-service")
public interface AppartementClient {

    @GetMapping("/{id}")
    ResponseEntity<AppartementDto> byId(@PathVariable Long id);
}
