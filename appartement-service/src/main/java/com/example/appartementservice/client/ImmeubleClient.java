package com.example.appartementservice.client;

import com.example.appartementservice.dto.ImmeubleDto;
import com.example.appartementservice.entity.enums.StatusImmeuble;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8187/api/v1/immeubles", name = "immeuble-service")
public interface ImmeubleClient {

    @PutMapping("/status/{id}")
    ResponseEntity<Void> updateStatusImmeuble(
            @PathVariable Long id,
            @RequestParam("statusImmeuble") StatusImmeuble statusImmeuble
    );

    @GetMapping("/{id}")
    ResponseEntity<ImmeubleDto> byId(@PathVariable Long id);
}
