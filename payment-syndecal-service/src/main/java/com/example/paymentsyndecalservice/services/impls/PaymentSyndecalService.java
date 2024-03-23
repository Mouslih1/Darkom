package com.example.paymentsyndecalservice.services.impls;

import com.example.paymentsyndecalservice.dtos.PaymentSyndecalDto;
import com.example.paymentsyndecalservice.entities.PaymentSyndecal;
import com.example.paymentsyndecalservice.exceptions.NotFoundException;
import com.example.paymentsyndecalservice.repositories.IPaymentSyndecalRepository;
import com.example.paymentsyndecalservice.services.IPaymentSyndecalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentSyndecalService implements IPaymentSyndecalService {

    private final ModelMapper modelMapper;
    private final IPaymentSyndecalRepository iPaymentSyndecalRepository;
    private static final String PAYMENT_SYNDECAL_NOT_FOUND = "Payment syndecal not found with this id : ";

    @Override
    public PaymentSyndecalDto save(PaymentSyndecalDto paymentSyndecalDto)
    {
        PaymentSyndecal paymentSyndecal = iPaymentSyndecalRepository.save(modelMapper.map(paymentSyndecalDto, PaymentSyndecal.class));
        return modelMapper.map(paymentSyndecal, PaymentSyndecalDto.class);
    }

    @Override
    public PaymentSyndecalDto update(Long id, PaymentSyndecalDto paymentSyndecalDto)
    {
        PaymentSyndecal paymentSyndecal = iPaymentSyndecalRepository.findById(id).orElseThrow(() -> new NotFoundException(PAYMENT_SYNDECAL_NOT_FOUND + id));

        paymentSyndecal.setDescription(paymentSyndecalDto.getDescription());
        paymentSyndecal.setMethodePaymentSyndecal(paymentSyndecalDto.getMethodePaymentSyndecal());
        paymentSyndecal.setStatusPaymentSyndecal(paymentSyndecalDto.getStatusPaymentSyndecal());
        paymentSyndecal.setTypePaymentSyndecal(paymentSyndecalDto.getTypePaymentSyndecal());
        paymentSyndecal.setMontantPaye(paymentSyndecalDto.getMontantPaye());

        PaymentSyndecal paymentSyndecalUpdated = iPaymentSyndecalRepository.save(paymentSyndecal);
        return modelMapper.map(paymentSyndecalUpdated, PaymentSyndecalDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iPaymentSyndecalRepository.deleteById(id);
    }

    @Override
    public List<PaymentSyndecalDto> all()
    {
        List<PaymentSyndecal> paymentSyndecals = iPaymentSyndecalRepository.findAll();
        return paymentSyndecals
                .stream()
                .map((paymentSyndecal) -> modelMapper.map(paymentSyndecal, PaymentSyndecalDto.class))
                .toList();
    }

    @Override
    public PaymentSyndecalDto byId(Long id)
    {
        PaymentSyndecal paymentSyndecal = iPaymentSyndecalRepository.findById(id).orElseThrow(() -> new NotFoundException(PAYMENT_SYNDECAL_NOT_FOUND + id));
        return modelMapper.map(paymentSyndecal, PaymentSyndecalDto.class);
    }
}
