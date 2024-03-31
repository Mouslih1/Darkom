package com.example.annonceservice.controller;

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
    public ResponseEntity<AnnonceResponse> save(
            @RequestHeader("agenceId") Long agenceId,
            @ModelAttribute @Valid AnnonceRequest annonceRequest)
    {
        return new ResponseEntity<>(iAnnonceService.save(agenceId, annonceRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnnonceResponse>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iAnnonceService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}/agence")
    public ResponseEntity<AnnonceResponse> byIdAndAgence(
            @PathVariable Long id,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iAnnonceService.byIdAndAgence(id, agenceId), HttpStatus.OK);
    }

    @GetMapping("/agence")
    public ResponseEntity<List<AnnonceResponse>> allByAgence(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(
                iAnnonceService.allByAgence(pageNo, pageSize, agenceId),
                HttpStatus.OK
        );
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
