package com.example.paymentcontratservice.controllers;

import com.example.paymentcontratservice.dtos.PaymentContratLoyerDto;
import com.example.paymentcontratservice.exceptions.Error;
import com.example.paymentcontratservice.services.IPaymentContratLoyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment-contrat-loyers")
public class PaymentContratLoyerController {

    private final IPaymentContratLoyerService iPaymentContratLoyerService;

    @PostMapping
    public ResponseEntity<PaymentContratLoyerDto> save(
            @RequestBody PaymentContratLoyerDto paymentContratLoyerDto
    )
    {
        return new ResponseEntity<>(iPaymentContratLoyerService.save(paymentContratLoyerDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentContratLoyerDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iPaymentContratLoyerService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentContratLoyerDto> update(
            @PathVariable Long id,
            @RequestBody PaymentContratLoyerDto paymentContratLoyerDto
    )
    {
        return new ResponseEntity<>(iPaymentContratLoyerService.update(id, paymentContratLoyerDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        iPaymentContratLoyerService.delete(id);
        return new ResponseEntity<>(new Error("Payment Contrat loyer deleted successfully."), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentContratLoyerDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iPaymentContratLoyerService.byId(id), HttpStatus.OK);
    }
}
