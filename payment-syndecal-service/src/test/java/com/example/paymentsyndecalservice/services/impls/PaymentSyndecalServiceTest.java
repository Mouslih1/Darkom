package com.example.paymentsyndecalservice.services.impls;

import com.example.paymentsyndecalservice.dtos.PaymentSyndecalDto;
import com.example.paymentsyndecalservice.entities.PaymentSyndecal;
import com.example.paymentsyndecalservice.entities.enums.MethodePaymentSyndecal;
import com.example.paymentsyndecalservice.producers.PaymentSyndicatProducer;
import com.example.paymentsyndecalservice.repositories.IPaymentSyndecalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentSyndecalServiceTest {

    @Mock
    private IPaymentSyndecalRepository iPaymentSyndecalRepository;

    @Mock
    private PaymentSyndicatProducer paymentSyndicatProducer;

    @InjectMocks
    private PaymentSyndecalService paymentSyndecalService;

    @BeforeEach
    public void setup()
    {
        iPaymentSyndecalRepository = Mockito.mock(IPaymentSyndecalRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        paymentSyndecalService = new PaymentSyndecalService(modelMapper, iPaymentSyndecalRepository, paymentSyndicatProducer);
    }

    @Test
    public void testSave()
    {
        PaymentSyndecal paymentSyndecal = new PaymentSyndecal();
        paymentSyndecal.setDescription("description 1");
        paymentSyndecal.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);


        PaymentSyndecalDto paymentSyndecalDto = new PaymentSyndecalDto();
        paymentSyndecalDto.setDescription("description 1");
        paymentSyndecalDto.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);

        when(iPaymentSyndecalRepository.save(Mockito.any(PaymentSyndecal.class))).thenReturn(paymentSyndecal);

        PaymentSyndecalDto paymentSyndecalDtoSaved = paymentSyndecalService.save(1L, paymentSyndecalDto, null);
        System.out.println(paymentSyndecalDtoSaved);
        System.out.println(paymentSyndecalDtoSaved.getDescription());
        Assertions.assertThat(paymentSyndecalDtoSaved.getId()).isEqualTo(paymentSyndecal.getId());
        Assertions.assertThat(paymentSyndecalDtoSaved).isNotNull();
    }

    @Test
    public void testById()
    {
        PaymentSyndecal paymentSyndecal = new PaymentSyndecal();
        paymentSyndecal.setDescription("description 1");
        paymentSyndecal.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);


        PaymentSyndecalDto paymentSyndecalDto = new PaymentSyndecalDto();
        paymentSyndecalDto.setDescription("description 1");
        paymentSyndecalDto.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);

        when(iPaymentSyndecalRepository.findById(1L)).thenReturn(Optional.of(paymentSyndecal));

        PaymentSyndecalDto paymentSyndecalDtoGet = paymentSyndecalService.byId(1L);
        Assertions.assertThat(paymentSyndecalDtoGet).isNotNull();
        Assertions.assertThat(paymentSyndecalDtoGet.getId()).isEqualTo(paymentSyndecal.getId());
    }


    @Test
    public void testUpdate()
    {
        PaymentSyndecal paymentSyndecal = new PaymentSyndecal();
        paymentSyndecal.setDescription("description 1");
        paymentSyndecal.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);


        PaymentSyndecalDto paymentSyndecalDto = new PaymentSyndecalDto();
        paymentSyndecalDto.setDescription("description 1");
        paymentSyndecalDto.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);

        when(iPaymentSyndecalRepository.save(Mockito.any(PaymentSyndecal.class))).thenReturn(paymentSyndecal);
        when(iPaymentSyndecalRepository.findById(1L)).thenReturn(Optional.of(paymentSyndecal));


        PaymentSyndecalDto paymentSyndecalDtoUpdated = paymentSyndecalService.update(1L, paymentSyndecalDto);
        System.out.println(paymentSyndecalDtoUpdated);
        System.out.println(paymentSyndecalDtoUpdated.getDescription());
        Assertions.assertThat(paymentSyndecalDtoUpdated).isNotNull();
        Assertions.assertThat(paymentSyndecalDtoUpdated.getDescription()).isEqualTo(paymentSyndecal.getDescription());
    }

    @Test
    public void testDelete()
    {
        PaymentSyndecal paymentSyndecal = new PaymentSyndecal();
        paymentSyndecal.setDescription("description 1");
        paymentSyndecal.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);




        when(iPaymentSyndecalRepository.findById(1L)).thenReturn(Optional.of(paymentSyndecal));

        assertAll(() -> paymentSyndecalService.delete(1L));
    }

    @Test
    public void testAll()
    {
        PaymentSyndecal paymentSyndecal = new PaymentSyndecal();
        paymentSyndecal.setDescription("description 1");
        paymentSyndecal.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);

        PaymentSyndecal paymentSyndecal1 = new PaymentSyndecal();
        paymentSyndecal1.setDescription("description 1");
        paymentSyndecal1.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);

        PaymentSyndecal paymentSyndecal2 = new PaymentSyndecal();
        paymentSyndecal2.setDescription("description 1");
        paymentSyndecal2.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);

        PaymentSyndecalDto paymentSyndecalDto = new PaymentSyndecalDto();
        paymentSyndecalDto.setDescription("description 1");
        paymentSyndecalDto.setMethodePaymentSyndecal(MethodePaymentSyndecal.ESPECES);



        List<PaymentSyndecal> paymentSyndecalList = Arrays.asList(paymentSyndecal, paymentSyndecal1, paymentSyndecal2);
        Page<PaymentSyndecal> paymentSyndecals = new PageImpl<>(paymentSyndecalList);

        when(iPaymentSyndecalRepository.findAll(any(Pageable.class))).thenReturn(paymentSyndecals);

        List<PaymentSyndecalDto> paymentSyndecalDtos = paymentSyndecalService.all(0, 11);

        assertNotNull(paymentSyndecalDtos);
        assertThat(paymentSyndecalDtos).isNotEmpty();
        assertThat(paymentSyndecalDtos).hasSize(3);
    }

}