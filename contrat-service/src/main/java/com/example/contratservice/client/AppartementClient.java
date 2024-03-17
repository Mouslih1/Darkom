package com.example.contratservice.client;

import com.example.contratservice.dto.AppartementDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(url = "http://localhost:8087/api/v1/appartements", name = "appartement-service")

public interface AppartementClient {

    @PutMapping("/etat/{id}")
    ResponseEntity<Void> updateEtatAppartementToOccuper(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<AppartementDto> byId(@PathVariable Long id);

    @GetMapping("/immeuble/{immeubleId}")
    ResponseEntity<List<AppartementDto>> byIdAndImmeuble(@PathVariable Long immeubleId);
}
