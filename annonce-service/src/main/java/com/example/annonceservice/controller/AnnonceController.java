package com.example.annonceservice.controller;

import com.example.annonceservice.dto.AnnonceDto;
import com.example.annonceservice.exception.Error;
import com.example.annonceservice.service.IAnnonceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/annonces")
public class AnnonceController {

    private final IAnnonceService iAnnonceService;

    @PostMapping
    public ResponseEntity<AnnonceDto> save(@RequestBody @Valid AnnonceDto annonceDto)
    {
        return new ResponseEntity<>(iAnnonceService.save(annonceDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnnonceDto>> all()
    {
        return new ResponseEntity<>(iAnnonceService.all(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iAnnonceService.byId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnonceDto> update(@PathVariable Long id, @RequestBody @Valid AnnonceDto annonceDto)
    {
        return new ResponseEntity<>(iAnnonceService.update(id, annonceDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Annonce deleted successfully.");
        iAnnonceService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
