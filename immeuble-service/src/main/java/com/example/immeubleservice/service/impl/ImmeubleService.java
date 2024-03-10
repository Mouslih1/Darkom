package com.example.immeubleservice.service.impl;

import com.example.immeubleservice.dto.ImmeubleDto;
import com.example.immeubleservice.entity.Immeuble;
import com.example.immeubleservice.exception.NotFoundException;
import com.example.immeubleservice.repository.IimeubleRepository;
import com.example.immeubleservice.service.IimmeubleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImmeubleService implements IimmeubleService {

    private final ModelMapper modelMapper;
    private  final IimeubleRepository iimeubleRepository;
    private static final String IMMEUBLE_NOT_FOUND = "Immeuble not found with this id : ";

    @Override
    public ImmeubleDto save(ImmeubleDto immeubleDto)
    {
        immeubleDto.setAgenceId(1L);
        Immeuble immeuble = iimeubleRepository.save(modelMapper.map(immeubleDto, Immeuble.class));
        return modelMapper.map(immeuble, ImmeubleDto.class);
    }

    @Override
    public ImmeubleDto update(Long id, ImmeubleDto immeubleDto)
    {
        Immeuble immeuble = iimeubleRepository.findById(id).orElseThrow(() -> new NotFoundException(IMMEUBLE_NOT_FOUND + id));

        immeuble.setReferenceImmeuble(immeubleDto.getReferenceImmeuble());
        immeuble.setAddress(immeubleDto.getAddress());
        immeuble.setNumberEtage(immeubleDto.getNumberEtage());
        immeuble.setNumberApparetement(immeubleDto.getNumberApparetement());
        immeuble.setAnneeConstruction(immeubleDto.getAnneeConstruction());
        immeuble.setAgenceId(1L);
        immeuble.setStatusImmeuble(immeubleDto.getStatusImmeuble());

        Immeuble immeubleUpdated = iimeubleRepository.save(immeuble);
        return modelMapper.map(immeubleUpdated, ImmeubleDto.class);
    }

    @Override
    public List<ImmeubleDto> all()
    {
        List<Immeuble> immeubles = iimeubleRepository.findAll();
        return immeubles
                .stream()
                .map((immeuble) -> modelMapper.map(immeuble, ImmeubleDto.class))
                .toList();
    }

    @Override
    public ImmeubleDto byId(Long id)
    {
        Immeuble immeuble = iimeubleRepository.findById(id).orElseThrow(() -> new NotFoundException(IMMEUBLE_NOT_FOUND + id));
        return modelMapper.map(immeuble, ImmeubleDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iimeubleRepository.deleteById(id);
    }
}
