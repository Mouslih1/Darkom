package com.example.travauxservice.service.impl;

import com.example.travauxservice.dto.TravauxDto;
import com.example.travauxservice.entity.Travaux;
import com.example.travauxservice.entity.enums.Etat;
import com.example.travauxservice.producers.TravauxProducer;
import com.example.travauxservice.repository.ITravauxRepository;
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
class TravauxServiceTest {

    @Mock
    private ITravauxRepository iTravauxRepository;

    @Mock
    private TravauxProducer travauxProducer;

    @InjectMocks
    private TravauxService travauxService;

    @BeforeEach
    public void setup()
    {
        iTravauxRepository = Mockito.mock(ITravauxRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        travauxService = new TravauxService(modelMapper, iTravauxRepository, travauxProducer);
    }

    @Test
    public void testSave()
    {
        Travaux travaux = new Travaux();
        travaux.setDescription("description 1");
        travaux.setEtat(Etat.URGENT);

        TravauxDto travauxDto = new TravauxDto();
        travauxDto.setDescription("description 1");
        travauxDto.setEtat(Etat.URGENT);

        when(iTravauxRepository.save(Mockito.any(Travaux.class))).thenReturn(travaux);

        TravauxDto travauxDtoSaved = travauxService.save(1L, null, travauxDto, null);
        System.out.println(travauxDtoSaved);
        System.out.println(travaux.getDescription());
        Assertions.assertThat(travauxDtoSaved.getId()).isEqualTo(travaux.getId());
        Assertions.assertThat(travauxDtoSaved).isNotNull();
    }

    @Test
    public void testById()
    {
        Travaux travaux = new Travaux();
        travaux.setDescription("description 1");
        travaux.setEtat(Etat.URGENT);

        TravauxDto travauxDto = new TravauxDto();
        travauxDto.setDescription("description 1");
        travauxDto.setEtat(Etat.URGENT);

        when(iTravauxRepository.findById(1L)).thenReturn(Optional.of(travaux));

        TravauxDto travauxDtoGet = travauxService.byId(1L);
        Assertions.assertThat(travauxDtoGet).isNotNull();
        Assertions.assertThat(travauxDtoGet.getId()).isEqualTo(travaux.getId());
    }


    @Test
    public void testUpdate()
    {
        Travaux travaux = new Travaux();
        travaux.setDescription("description 1");
        travaux.setEtat(Etat.URGENT);

        TravauxDto travauxDto = new TravauxDto();
        travauxDto.setDescription("description 1");
        travauxDto.setEtat(Etat.URGENT);

        when(iTravauxRepository.save(Mockito.any(Travaux.class))).thenReturn(travaux);
        when(iTravauxRepository.findById(1L)).thenReturn(Optional.of(travaux));


        TravauxDto travauxDtoUpdated = travauxService.update(1L, travauxDto);
        System.out.println(travauxDtoUpdated);
        System.out.println(travauxDtoUpdated.getDescription());
        Assertions.assertThat(travauxDtoUpdated).isNotNull();
        Assertions.assertThat(travauxDtoUpdated.getDescription()).isEqualTo(travaux.getDescription());
    }

    @Test
    public void testDelete()
    {

        Travaux travaux = new Travaux();
        travaux.setDescription("description 1");
        travaux.setEtat(Etat.URGENT);


        when(iTravauxRepository.findById(1L)).thenReturn(Optional.of(travaux));

        assertAll(() -> travauxService.delete(1L));
    }

    @Test
    public void testAll()
    {
        Travaux travaux = new Travaux();
        travaux.setDescription("description 1");
        travaux.setEtat(Etat.URGENT);

        Travaux travaux1 = new Travaux();
        travaux1.setDescription("description 1");
        travaux1.setEtat(Etat.URGENT);

        Travaux travaux2 = new Travaux();
        travaux2.setDescription("description 1");
        travaux2.setEtat(Etat.URGENT);

        TravauxDto travauxDto = new TravauxDto();
        travauxDto.setDescription("description 1");
        travauxDto.setEtat(Etat.URGENT);



        List<Travaux> travauxes = Arrays.asList(travaux, travaux2, travaux1);
        Page<Travaux> travauxesList = new PageImpl<>(travauxes);

        when(iTravauxRepository.findAll(any(Pageable.class))).thenReturn(travauxesList);

        List<TravauxDto> travauxDtos = travauxService.all(0, 11);

        assertNotNull(travauxDtos);
        assertThat(travauxDtos).isNotEmpty();
        assertThat(travauxDtos).hasSize(3);
    }

}