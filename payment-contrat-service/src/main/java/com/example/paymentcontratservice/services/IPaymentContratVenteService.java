package com.example.paymentcontratservice.services;

import com.example.paymentcontratservice.dtos.PaymentContratLoyerDto;
import com.example.paymentcontratservice.dtos.PaymentContratVenteDto;
import com.example.paymentcontratservice.entities.PaymentContratVente;

import java.util.List;

public interface IPaymentContratVenteService {

    PaymentContratVenteDto save(PaymentContratVenteDto paymentContratVenteDto);
    PaymentContratVenteDto update(Long id, PaymentContratVenteDto paymentContratVenteDto);
    void delete(Long id);
    List<PaymentContratVenteDto> all(int pageNo, int pageSize);
    PaymentContratVenteDto byId(Long id);
}
