package com.example.paymentsyndecalservice.services.impls;

import com.example.paymentsyndecalservice.dtos.PaymentSyndecalDto;
import com.example.paymentsyndecalservice.dtos.PaymentSyndicatProducerDto;
import com.example.paymentsyndecalservice.entities.PaymentSyndecal;
import com.example.paymentsyndecalservice.exceptions.NotFoundException;
import com.example.paymentsyndecalservice.producers.PaymentSyndicatProducer;
import com.example.paymentsyndecalservice.repositories.IPaymentSyndecalRepository;
import com.example.paymentsyndecalservice.services.IPaymentSyndecalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentSyndecalService implements IPaymentSyndecalService {

    private final ModelMapper modelMapper;
    private final IPaymentSyndecalRepository iPaymentSyndecalRepository;
    private final PaymentSyndicatProducer paymentSyndicatProducer;
    private static final String PAYMENT_SYNDECAL_NOT_FOUND = "Payment syndecal not found with this id : ";
    private static final String PAYMENT_SYNDECAL_OR_AGENCE_NOT_FOUND = "Payment syndecal or agence not found with this id : ";


    @Override
    public PaymentSyndecalDto save(Long agenceId,Long userCreateNotification, PaymentSyndecalDto paymentSyndecalDto, String authorization)
    {
        paymentSyndecalDto.setAgenceId(agenceId);
        PaymentSyndecal paymentSyndecal = iPaymentSyndecalRepository.save(modelMapper.map(paymentSyndecalDto, PaymentSyndecal.class));

        System.out.println("payment : " + userCreateNotification  + paymentSyndecal.getPayerId());
        paymentSyndicatProducer.producerMessage(
                new PaymentSyndicatProducerDto(
                        paymentSyndecal.getId(),
                        "Create an payment syndicat",
                        paymentSyndecal.getAgentCreatedBy(),
                        userCreateNotification,
                        null,
                        paymentSyndecal.getAgenceId(),
                        authorization,
                        paymentSyndecal.getPayerId()
                )
        );
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
    public List<PaymentSyndecalDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<PaymentSyndecal> paymentSyndecals = iPaymentSyndecalRepository.findAll(pageable);

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

    @Override
    public List<PaymentSyndecalDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<PaymentSyndecal> paymentSyndecates = iPaymentSyndecalRepository.findByAgenceId(agenceId);

        List<PaymentSyndecal> paginatedPaymentSyndecates = paymentSyndecates.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedPaymentSyndecates
                .stream()
                .map((paymentSyndecal) -> modelMapper.map(paymentSyndecal, PaymentSyndecalDto.class))
                .toList();
    }

    @Override
    public PaymentSyndecalDto byIdByAgence(Long paymentSyndecalId, Long agenceId)
    {
        PaymentSyndecal paymentSyndecal = iPaymentSyndecalRepository.findByIdAndAgenceId(paymentSyndecalId, agenceId)
                .orElseThrow(() -> new NotFoundException(PAYMENT_SYNDECAL_OR_AGENCE_NOT_FOUND + paymentSyndecalId + "agence : " + agenceId));
        return modelMapper.map(paymentSyndecal, PaymentSyndecalDto.class);
    }
}
