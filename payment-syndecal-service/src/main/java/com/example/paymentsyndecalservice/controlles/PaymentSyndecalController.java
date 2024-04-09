package com.example.paymentsyndecalservice.controlles;

import com.example.paymentsyndecalservice.dtos.PaymentSyndecalDto;
import com.example.paymentsyndecalservice.exceptions.Error;
import com.example.paymentsyndecalservice.services.IPaymentSyndecalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment-syndecals")
@CrossOrigin("*")
public class PaymentSyndecalController {

    private final IPaymentSyndecalService iPaymentSyndecalService;

    @PostMapping
    public ResponseEntity<PaymentSyndecalDto> save(
            @RequestBody PaymentSyndecalDto paymentSyndecalDto,
            @RequestHeader("agenceId") Long agenceId,
            @RequestHeader("id") Long userCreateNotification,
            @RequestHeader("Authorization") String authorization
    )
    {
        return new ResponseEntity<>(iPaymentSyndecalService.save(agenceId,userCreateNotification, paymentSyndecalDto, authorization), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentSyndecalDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iPaymentSyndecalService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentSyndecalDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iPaymentSyndecalService.byId(id), HttpStatus.OK);
    }

    @GetMapping("/agence")
    public ResponseEntity<List<PaymentSyndecalDto>> allByAgence(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iPaymentSyndecalService.allByAgence(agenceId,pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}/agence")
    public ResponseEntity<PaymentSyndecalDto> byIdByAgence(
            @PathVariable Long id,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iPaymentSyndecalService.byIdByAgence(id, agenceId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentSyndecalDto> update(
            @PathVariable Long id,
            @RequestBody PaymentSyndecalDto paymentSyndecalDto
    )
    {
        return new ResponseEntity<>(iPaymentSyndecalService.update(id, paymentSyndecalDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Payment syndecal deleted successfully.");
        iPaymentSyndecalService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
