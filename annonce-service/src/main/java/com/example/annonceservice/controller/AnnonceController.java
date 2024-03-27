package com.example.annonceservice.controller;

import com.example.annonceservice.dto.AnnonceDto;
import com.example.annonceservice.dto.AnnonceRequest;
import com.example.annonceservice.dto.AnnonceRequestPhoto;
import com.example.annonceservice.dto.AnnonceResponse;
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
    public ResponseEntity<AnnonceResponse> save(@ModelAttribute @Valid AnnonceRequest annonceRequest)
    {
        return new ResponseEntity<>(iAnnonceService.save(annonceRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnnonceResponse>> all()
    {
        return new ResponseEntity<>(iAnnonceService.all(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceResponse> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iAnnonceService.byId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnonceResponse> update(@PathVariable Long id, @RequestBody @Valid AnnonceRequest annonceRequest)
    {
        return new ResponseEntity<>(iAnnonceService.update(id, annonceRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Annonce deleted successfully.");
        iAnnonceService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @PutMapping("/photo/{id}")
    public ResponseEntity<AnnonceResponse> updatePhotoProfil(
            @PathVariable Long id,
            @ModelAttribute @Valid AnnonceRequestPhoto annonceRequestPhoto
    )
    {
        return new ResponseEntity<>(iAnnonceService.updateAnnoncePhoto(id, annonceRequestPhoto), HttpStatus.OK);
    }
}
