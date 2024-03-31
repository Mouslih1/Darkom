package com.example.immeubleservice.service.impl;

import com.example.immeubleservice.dto.ImmeubleDto;
import com.example.immeubleservice.entity.Immeuble;
import com.example.immeubleservice.entity.enums.StatusImmeuble;
import com.example.immeubleservice.exception.NotFoundException;
import com.example.immeubleservice.repository.IimeubleRepository;
import com.example.immeubleservice.service.IimmeubleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private static final String IMMEUBLE_OR_AGENCE_NOT_FOUND = "Immeuble or agence not found with this id : ";


    @Override
    public ImmeubleDto save(Long agenceId, ImmeubleDto immeubleDto)
    {
        immeubleDto.setAgenceId(agenceId);
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
        immeuble.setStatusImmeuble(immeubleDto.getStatusImmeuble());

        Immeuble immeubleUpdated = iimeubleRepository.save(immeuble);
        return modelMapper.map(immeubleUpdated, ImmeubleDto.class);
    }

    @Override
    public List<ImmeubleDto> all(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Immeuble> immeubles = iimeubleRepository.findAll(pageable);
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
    public List<ImmeubleDto> allByAgence(Long agenceId, int pageNo, int pageSize)
    {
        int startIndex = (pageNo) * pageSize;
        List<Immeuble> immeubles = iimeubleRepository.findByAgenceId(agenceId);

        List<Immeuble> paginatedImmeubles = immeubles.stream()
                .skip(startIndex)
                .limit(pageSize)
                .toList();

        return paginatedImmeubles
                .stream()
                .map((immeuble) -> modelMapper.map(immeuble, ImmeubleDto.class))
                .toList();
    }

    @Override
    public ImmeubleDto byIdAndAgence(Long id, Long agenceId)
    {
        Immeuble immeuble = iimeubleRepository.findByIdAndAgenceId(id, agenceId)
                .orElseThrow(() -> new NotFoundException(IMMEUBLE_OR_AGENCE_NOT_FOUND + id + "agence : " + agenceId));
        return modelMapper.map(immeuble, ImmeubleDto.class);
    }

    @Override
    public void delete(Long id)
    {
        iimeubleRepository.deleteById(id);
    }

    @Override
    public void updateEtatImmeuble(Long id, StatusImmeuble statusImmeuble)
    {
        Immeuble immeuble = iimeubleRepository.findById(id).orElseThrow(() -> new NotFoundException(IMMEUBLE_NOT_FOUND + id));
        immeuble.setStatusImmeuble(statusImmeuble);
        iimeubleRepository.save(immeuble);
    }
}
