package com.example.paymentcontratservice.services.impl;

import com.example.paymentcontratservice.clients.ContratClient;
import com.example.paymentcontratservice.dtos.ContratDto;
import com.example.paymentcontratservice.dtos.PaymentContratLoyerDto;
import com.example.paymentcontratservice.entities.PaymentContratLoyer;
import com.example.paymentcontratservice.entities.enums.TypeContrat;
import com.example.paymentcontratservice.exceptions.NotFoundException;
import com.example.paymentcontratservice.repositories.IPaymentContratLoyerRepository;
import com.example.paymentcontratservice.services.IPaymentContratLoyerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentContratLoyerService implements IPaymentContratLoyerService {

    private final IPaymentContratLoyerRepository iPaymentContratLoyerRepository;
    private final ModelMapper modelMapper;
    private final ContratClient contratClient;
    private static final String PAYMENT_CONTRAT_LOYER_NOT_FOUND = "Payment contrat loyer not found with this id : ";
    private static final String PAYMENT_CONTRAT_LOYER_OR_AGENCE_NOT_FOUND = "Payment or agence contrat loyer not found with this id : ";

    @Override
    public PaymentContratLoyerDto save(Long agenceId, PaymentContratLoyerDto paymentContratLoyerDto)
    {
        paymentContratLoyerDto.setAgenceId(agenceId);

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
                .orElseThrow(() -> new NotFoundException(PAYMENT_CONTRAT_LOYER_NOT_FOUND + id));

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
    public List<PaymentContratLoyerDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<PaymentContratLoyer> paymentContratLoyers = iPaymentContratLoyerRepository.findAll(pageable);

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

    @Override
    public List<PaymentContratLoyerDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<PaymentContratLoyer> paymentContratLoyers = iPaymentContratLoyerRepository.findByAgenceId(agenceId);

        List<PaymentContratLoyer> paginatedPaymentsContractsLoyers = paymentContratLoyers.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedPaymentsContractsLoyers
                .stream()
                .map((paymentContratLoyer) -> modelMapper.map(paymentContratLoyer, PaymentContratLoyerDto.class))
                .toList();
    }

    @Override
    public PaymentContratLoyerDto byIdByAgence(Long paymentContratLoyerId, Long agenceId)
    {
        PaymentContratLoyer paymentContratLoyer = iPaymentContratLoyerRepository.findByIdAndAgenceId(paymentContratLoyerId, agenceId)
                .orElseThrow(() -> new NotFoundException(PAYMENT_CONTRAT_LOYER_OR_AGENCE_NOT_FOUND + paymentContratLoyerId + "agence : " + agenceId));
        return modelMapper.map(paymentContratLoyer, PaymentContratLoyerDto.class);
    }
}
