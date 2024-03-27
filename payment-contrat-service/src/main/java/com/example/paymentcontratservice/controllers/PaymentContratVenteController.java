package com.example.paymentcontratservice.controllers;


import com.example.paymentcontratservice.dtos.PaymentContratVenteDto;
import com.example.paymentcontratservice.exceptions.Error;
import com.example.paymentcontratservice.services.IPaymentContratVenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment-contrat-ventes")
public class PaymentContratVenteController {

    private final IPaymentContratVenteService iPaymentContratVenteService;

    @PostMapping
    public ResponseEntity<PaymentContratVenteDto> save(
            @RequestBody PaymentContratVenteDto paymentContratVenteDto
    )
    {
        return new ResponseEntity<>(iPaymentContratVenteService.save(paymentContratVenteDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentContratVenteDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iPaymentContratVenteService.all(pageNo,  pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentContratVenteDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iPaymentContratVenteService.byId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentContratVenteDto> update(
            @PathVariable Long id,
            @RequestBody PaymentContratVenteDto paymentContratVenteDto
    )
    {
        return new ResponseEntity<>(iPaymentContratVenteService.update(id, paymentContratVenteDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        iPaymentContratVenteService.delete(id);
        return new ResponseEntity<>(new Error("Payment contrat vente deleted successfully."), HttpStatus.OK);
    }
}
