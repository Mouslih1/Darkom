package com.example.paymentcontratservice.services.impl;

import com.example.paymentcontratservice.clients.ContratClient;
import com.example.paymentcontratservice.dtos.ContratDto;
import com.example.paymentcontratservice.dtos.PaymentContratVenteDto;
import com.example.paymentcontratservice.entities.PaymentContrat;
import com.example.paymentcontratservice.entities.PaymentContratVente;
import com.example.paymentcontratservice.exceptions.NotFoundException;
import com.example.paymentcontratservice.repositories.IPaymentContratVenteRepository;
import com.example.paymentcontratservice.services.IPaymentContratVenteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentContratVenteService implements IPaymentContratVenteService {

    private final ModelMapper modelMapper;
    private final IPaymentContratVenteRepository iPaymentContratVenteRepository;
    private final ContratClient contratClient;
    private static final String PAYMENT_CONTRAT_VENTE_NOT_FOUND = "Payment contrat vente not found with this id : ";

    @Override
    public PaymentContratVenteDto save(PaymentContratVenteDto paymentContratVenteDto)
    {
        ContratDto contratDto = contratClient.byId(paymentContratVenteDto.getContratId()).getBody();
        assert contratDto != null;
        paymentContratVenteDto.setMontantRester(contratDto.getMontant() - paymentContratVenteDto.getMontantPaye());
        PaymentContrat paymentContratSaved = iPaymentContratVenteRepository.save(modelMapper.map(paymentContratVenteDto, PaymentContratVente.class));
        return modelMapper.map(paymentContratSaved, PaymentContratVenteDto.class);
    }

    @Override
    public PaymentContratVenteDto update(Long id, PaymentContratVenteDto paymentContratVenteDto)
    {
        PaymentContratVente paymentContratVente = iPaymentContratVenteRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(PAYMENT_CONTRAT_VENTE_NOT_FOUND + id));

        paymentContratVente.setContratId(paymentContratVenteDto.getContratId());
        paymentContratVente.setMethodePaymentContratVente(paymentContratVenteDto.getMethodePaymentContratVente());
        paymentContratVente.setTypePaymentContratVente(paymentContratVenteDto.getTypePaymentContratVente());
        paymentContratVente.setStatusPaymentContrat(paymentContratVenteDto.getStatusPaymentContrat());
        paymentContratVente.setMontantPaye(paymentContratVenteDto.getMontantPaye());
        ContratDto contratDto = contratClient.byId(paymentContratVente.getContratId()).getBody();
        assert contratDto != null;
        paymentContratVente.setMontantRester(contratDto.getMontant() - paymentContratVenteDto.getMontantPaye());

        PaymentContratVente paymentContratVenteUpdated = iPaymentContratVenteRepository.save(paymentContratVente);
        return modelMapper.map(paymentContratVenteUpdated, PaymentContratVenteDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iPaymentContratVenteRepository.deleteById(id);
    }

    @Override
    public List<PaymentContratVenteDto> all()
    {
        List<PaymentContratVente> paymentContratVentes = iPaymentContratVenteRepository.findAll();
        return paymentContratVentes
                .stream()
                .map((element) -> modelMapper.map(element, PaymentContratVenteDto.class))
                .toList();
    }

    @Override
    public PaymentContratVenteDto byId(Long id)
    {
        PaymentContratVente paymentContratVente = iPaymentContratVenteRepository.findById(id).orElseThrow(() -> new NotFoundException(PAYMENT_CONTRAT_VENTE_NOT_FOUND + id));
        return modelMapper.map(paymentContratVente, PaymentContratVenteDto.class);
    }
}
