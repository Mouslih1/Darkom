package com.example.paymentcontratservice.services;

import com.example.paymentcontratservice.dtos.PaymentContratLoyerDto;

import java.util.List;

public interface IPaymentContratLoyerService {

    PaymentContratLoyerDto save(Long agenceId, PaymentContratLoyerDto paymentContratLoyerDto);
    PaymentContratLoyerDto update(Long id, PaymentContratLoyerDto paymentContratLoyerDto);
    void delete(Long id);
    List<PaymentContratLoyerDto> all(int pageNo, int pageSize);
    PaymentContratLoyerDto byId(Long id);
    List<PaymentContratLoyerDto> allByAgence(Long agenceId, int pageNo, int pageSize);
    PaymentContratLoyerDto byIdByAgence(Long id, Long agenceId);
}
