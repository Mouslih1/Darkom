package com.example.plainte.service.impl;

import com.example.plainte.dto.PlainteDto;
import com.example.plainte.entity.Plainte;
import com.example.plainte.repository.IPlainteRepository;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PlainteServiceTest {

    @Mock
    private IPlainteRepository iPlainteRepository;

    @InjectMocks
    private PlainteService plainteService;

    @BeforeEach
    public void setup()
    {
        iPlainteRepository = Mockito.mock(IPlainteRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        plainteService = new PlainteService(iPlainteRepository, modelMapper);
    }

    @Test
    public void testSave()
    {
        Plainte plainte = new Plainte();
        plainte.setDescription("description 1");
        plainte.setSujet("sujet 1");

        PlainteDto plainteDto = new PlainteDto();
        plainteDto.setDescription("description 1");
        plainteDto.setSujet("sujet 1");

        when(iPlainteRepository.save(Mockito.any(Plainte.class))).thenReturn(plainte);

        PlainteDto plainteDtoSaved = plainteService.save(1L, plainteDto);
        System.out.println(plainteDtoSaved);
        System.out.println(plainte.getSujet());
        Assertions.assertThat(plainteDtoSaved.getId()).isEqualTo(plainte.getId());
        Assertions.assertThat(plainteDtoSaved).isNotNull();
    }

    @Test
    public void testById()
    {
        Plainte plainte = new Plainte();
        plainte.setDescription("description 1");
        plainte.setSujet("sujet 1");

        PlainteDto plainteDto = new PlainteDto();
        plainteDto.setDescription("description 1");
        plainteDto.setSujet("sujet 1");

        when(iPlainteRepository.findById(1L)).thenReturn(Optional.of(plainte));

        PlainteDto plainteDtoGet = plainteService.byId(1L);
        Assertions.assertThat(plainteDtoGet).isNotNull();
        Assertions.assertThat(plainteDtoGet.getId()).isEqualTo(plainte.getId());
    }


    @Test
    public void testUpdate()
    {
        Plainte plainte = new Plainte();
        plainte.setDescription("description 1");
        plainte.setSujet("sujet 1");

        PlainteDto plainteDto = new PlainteDto();
        plainteDto.setDescription("description 1");
        plainteDto.setSujet("sujet 1");

        when(iPlainteRepository.save(Mockito.any(Plainte.class))).thenReturn(plainte);
        when(iPlainteRepository.findById(1L)).thenReturn(Optional.of(plainte));


        PlainteDto plainteDtoUpdated = plainteService.update(1L, plainteDto);
        System.out.println(plainteDtoUpdated);
        System.out.println(plainteDtoUpdated.getSujet());
        Assertions.assertThat(plainteDtoUpdated).isNotNull();
        Assertions.assertThat(plainteDtoUpdated.getSujet()).isEqualTo(plainte.getSujet());
    }

    @Test
    public void testDelete()
    {
        Plainte plainte = new Plainte();
        plainte.setDescription("description 1");
        plainte.setSujet("sujet 1");


        when(iPlainteRepository.findById(1L)).thenReturn(Optional.of(plainte));

        assertAll(() -> plainteService.delete(1L));
    }

    @Test
    public void testAll()
    {
        Plainte plainte = new Plainte();
        plainte.setDescription("description 1");
        plainte.setSujet("sujet 1");

        Plainte plainte1 = new Plainte();
        plainte1.setDescription("description 1");
        plainte1.setSujet("sujet 1");

        Plainte plainte2 = new Plainte();
        plainte2.setDescription("description 1");
        plainte2.setSujet("sujet 1");

        PlainteDto plainteDto = new PlainteDto();
        plainteDto.setDescription("description 1");
        plainteDto.setSujet("sujet 1");



        List<Plainte> plainteList = Arrays.asList(plainte, plainte2, plainte1);
        Page<Plainte> plaintes = new PageImpl<>(plainteList);

        when(iPlainteRepository.findAll(any(Pageable.class))).thenReturn(plaintes);

        List<PlainteDto> plainteDtos = plainteService.all(0, 11);

        assertNotNull(plainteDtos);
        assertThat(plainteDtos).isNotEmpty();
        assertThat(plainteDtos).hasSize(3);
    }
}