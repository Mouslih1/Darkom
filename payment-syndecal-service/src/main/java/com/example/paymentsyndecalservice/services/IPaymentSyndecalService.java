package com.example.paymentsyndecalservice.services;

import com.example.paymentsyndecalservice.dtos.PaymentSyndecalDto;

import java.util.List;

public interface IPaymentSyndecalService {

    PaymentSyndecalDto save(Long agenceId, PaymentSyndecalDto paymentSyndecalDto, String authorization);
    PaymentSyndecalDto update(Long id, PaymentSyndecalDto paymentSyndecalDto);
    void delete(Long id);
    List<PaymentSyndecalDto> all(int pageNo, int pageSize);
    PaymentSyndecalDto byId(Long id);
    List<PaymentSyndecalDto> allByAgence(Long agenceId, int pageNo, int pageSize);
    PaymentSyndecalDto byIdByAgence(Long id, Long agenceId);
}
