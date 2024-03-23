package com.example.paymentsyndecalservice.services;

import com.example.paymentsyndecalservice.dtos.PaymentSyndecalDto;

import java.util.List;

public interface IPaymentSyndecalService {

    PaymentSyndecalDto save(PaymentSyndecalDto paymentSyndecalDto);
    PaymentSyndecalDto update(Long id, PaymentSyndecalDto paymentSyndecalDto);
    void delete(Long id);
    List<PaymentSyndecalDto> all();
    PaymentSyndecalDto byId(Long id);
}
