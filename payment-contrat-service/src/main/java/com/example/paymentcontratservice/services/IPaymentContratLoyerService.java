package com.example.paymentcontratservice.services;

import com.example.paymentcontratservice.dtos.PaymentContratLoyerDto;

import java.util.List;

public interface IPaymentContratLoyerService {

    PaymentContratLoyerDto save(PaymentContratLoyerDto paymentContratLoyerDto);
    PaymentContratLoyerDto update(Long id, PaymentContratLoyerDto paymentContratLoyerDto);
    void delete(Long id);
    List<PaymentContratLoyerDto> all(int pageNo, int pageSize);
    PaymentContratLoyerDto byId(Long id);
}
