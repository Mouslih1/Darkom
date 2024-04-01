package com.example.evenementservice.service.impl;

import com.example.evenementservice.producers.EvenementProducer;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.example.evenementservice.dto.EvenementDto;
import com.example.evenementservice.entity.Evenement;
import com.example.evenementservice.repository.IEvenementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

@SpringBootTest
class EvenementServiceTest {

    @Mock
    private IEvenementRepository iEvenementRepository;

    @Mock
    private EvenementProducer evenementProducer;

    @InjectMocks
    private EvenementService evenementService;

    @BeforeEach
    public void setup()
    {
        iEvenementRepository = Mockito.mock(IEvenementRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        evenementService = new EvenementService(iEvenementRepository, modelMapper, evenementProducer);
    }

    @Test
    public void testSave()
    {
        Evenement evenement = new Evenement();
        evenement.setSujet("Sujet 1");
        evenement.setDescription("description 1");

        EvenementDto evenementDto = new EvenementDto();
        evenementDto.setSujet("Sujet 1");
        evenementDto.setDescription("description 1");

        when(iEvenementRepository.save(Mockito.any(Evenement.class))).thenReturn(evenement);

        EvenementDto evenementSaved = evenementService.save(1L, evenementDto, null);
        System.out.println(evenementSaved);
        System.out.println(evenement.getSujet());
        Assertions.assertThat(evenementSaved.getId()).isEqualTo(evenement.getId());
        Assertions.assertThat(evenementSaved).isNotNull();
    }

    @Test
    public void testById()
    {
        Evenement evenement = new Evenement();
        evenement.setSujet("Sujet 1");
        evenement.setDescription("description 1");

        EvenementDto evenementDto = new EvenementDto();
        evenementDto.setSujet("Sujet 1");
        evenementDto.setDescription("description 1");

        when(iEvenementRepository.findById(1L)).thenReturn(Optional.of(evenement));

        EvenementDto evenementDtoSaved = evenementService.byId(1L);
        Assertions.assertThat(evenementDtoSaved).isNotNull();
        Assertions.assertThat(evenementDtoSaved.getId()).isEqualTo(evenement.getId());
    }


    @Test
    public void testUpdate()
    {
        Evenement evenement = new Evenement();
        evenement.setSujet("Sujet 1");
        evenement.setDescription("description 1");

        EvenementDto evenementDto = new EvenementDto();
        evenementDto.setSujet("Sujet 1");
        evenementDto.setDescription("description 1");

        when(iEvenementRepository.save(Mockito.any(Evenement.class))).thenReturn(evenement);
        when(iEvenementRepository.findById(1L)).thenReturn(Optional.of(evenement));


        EvenementDto evenementDtoUpdated = evenementService.update(1L, evenementDto);
        System.out.println(evenementDtoUpdated);
        System.out.println(evenementDtoUpdated.getSujet());
        Assertions.assertThat(evenementDtoUpdated).isNotNull();
        Assertions.assertThat(evenementDtoUpdated.getSujet()).isEqualTo(evenement.getSujet());
    }

    @Test
    public void testDelete()
    {
        Evenement evenement = new Evenement();
        evenement.setSujet("Sujet 1");
        evenement.setDescription("description 1");


        when(iEvenementRepository.findById(1L)).thenReturn(Optional.of(evenement));

        assertAll(() -> evenementService.delete(1L));
    }

    @Test
    public void testAll()
    {
        Evenement evenement = new Evenement();
        evenement.setSujet("Sujet 1");
        evenement.setDescription("description 1");

        Evenement evenement1 = new Evenement();
        evenement1.setSujet("Sujet 1");
        evenement1.setDescription("description 1");

        Evenement evenement2 = new Evenement();
        evenement2.setSujet("Sujet 1");
        evenement2.setDescription("description 1");

        List<Evenement> evenementList = Arrays.asList(evenement, evenement1, evenement2);
        Page<Evenement> evenementPage = new PageImpl<>(evenementList);

        when(iEvenementRepository.findAll(any(Pageable.class))).thenReturn(evenementPage);

        List<EvenementDto> evenementDtoList = evenementService.all(0, 11);

        assertNotNull(evenementDtoList);
        assertThat(evenementDtoList).isNotEmpty();
        assertThat(evenementDtoList).hasSize(3);
    }
}