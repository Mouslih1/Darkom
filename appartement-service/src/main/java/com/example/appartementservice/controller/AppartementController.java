package com.example.appartementservice.controller;

import com.example.appartementservice.dto.AppartementDto;
import com.example.appartementservice.exception.Error;
import com.example.appartementservice.service.IAppartementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appartements")
@RequiredArgsConstructor
public class AppartementController {

    private final IAppartementService iAppartementService;

    @PostMapping
    public ResponseEntity<AppartementDto> save(@RequestBody @Valid AppartementDto appartementDto)
    {
        return new ResponseEntity<>(iAppartementService.save(appartementDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppartementDto> update(
            @PathVariable Long id,
            @RequestBody @Valid AppartementDto appartementDto
    )
    {
        return new ResponseEntity<>(iAppartementService.update(id, appartementDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Appartement deleted successfully.");
        iAppartementService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AppartementDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iAppartementService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppartementDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iAppartementService.byId(id), HttpStatus.OK);
    }

    @GetMapping("/immeuble/{immeubleId}")
    public ResponseEntity<List<AppartementDto>> byIdAndImmeuble(@PathVariable Long immeubleId)
    {
        return new ResponseEntity<>(iAppartementService.byIdAndImmeuble(immeubleId), HttpStatus.OK);
    }

    @PutMapping("/etat/{id}")
    public ResponseEntity<Void> updateEtatAppartementToOccuper(@PathVariable Long id)
    {
        iAppartementService.updateEtatAppartementToOccuper(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
