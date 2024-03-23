package com.example.paymentcontratservice.services.impl;

import com.example.paymentcontratservice.dtos.PaymentContratLoyerDto;
import com.example.paymentcontratservice.entities.PaymentContratLoyer;
import com.example.paymentcontratservice.exceptions.NotFoundException;
import com.example.paymentcontratservice.repositories.IPaymentContratLoyerRepository;
import com.example.paymentcontratservice.services.IPaymentContratLoyerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentContratLoyerService implements IPaymentContratLoyerService {

    private final IPaymentContratLoyerRepository iPaymentContratLoyerRepository;
    private final ModelMapper modelMapper;
    private static final String PAYMENT_CONTRAT_LOYER_NOT_FOUND = "Payment contrat loyer not found with this id : ";

    @Override
    public PaymentContratLoyerDto save(PaymentContratLoyerDto paymentContratLoyerDto)
    {
        PaymentContratLoyer paymentContratLoyer = iPaymentContratLoyerRepository.save(modelMapper.map(paymentContratLoyerDto, PaymentContratLoyer.class));
        return modelMapper.map(paymentContratLoyer, PaymentContratLoyerDto.class);
    }

    @Override
    public PaymentContratLoyerDto update(
            Long id,
            PaymentContratLoyerDto paymentContratLoyerDto
    )
    {
        PaymentContratLoyer paymentContratLoyer = iPaymentContratLoyerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(PAYMENT_CONTRAT_LOYER_NOT_FOUND));

        paymentContratLoyer.setContratId(paymentContratLoyerDto.getContratId());
        paymentContratLoyer.setMethodePaymentContratLoyer(paymentContratLoyerDto.getMethodePaymentContratLoyer());
        paymentContratLoyer.setTypePaymentContratLoyer(paymentContratLoyerDto.getTypePaymentContratLoyer());
        paymentContratLoyer.setStatusPaymentContrat(paymentContratLoyerDto.getStatusPaymentContrat());
        paymentContratLoyer.setMontantPaye(paymentContratLoyerDto.getMontantPaye());

        PaymentContratLoyer paymentContratLoyerUpdated = iPaymentContratLoyerRepository.save(paymentContratLoyer);

        return modelMapper.map(paymentContratLoyerUpdated, PaymentContratLoyerDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iPaymentContratLoyerRepository.deleteById(id);
    }

    @Override
    public List<PaymentContratLoyerDto> all()
    {
        List<PaymentContratLoyer> paymentContratLoyers = iPaymentContratLoyerRepository.findAll();
        return paymentContratLoyers
                .stream()
                .map((element) -> modelMapper.map(element, PaymentContratLoyerDto.class))
                .toList();
    }

    @Override
    public PaymentContratLoyerDto byId(Long id)
    {
        PaymentContratLoyer paymentContratLoyer = iPaymentContratLoyerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(PAYMENT_CONTRAT_LOYER_NOT_FOUND + id));
        return modelMapper.map(paymentContratLoyer, PaymentContratLoyerDto.class);
    }
}
