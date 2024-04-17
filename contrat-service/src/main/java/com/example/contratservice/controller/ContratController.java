package com.example.contratservice.controller;

import com.example.contratservice.dto.ContratDto;
import com.example.contratservice.exception.Error;
import com.example.contratservice.service.IContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contrats")
@CrossOrigin("*")
public class ContratController {

    private final IContratService iContratService;

    @PostMapping
    public ResponseEntity<ContratDto> save(
            @RequestBody @Valid ContratDto contratDto,
            @RequestHeader("agenceId") Long agenceId,
            @RequestHeader("Authorization") String authorization
    )
    {
        return new ResponseEntity<>(iContratService.save(agenceId, contratDto, authorization), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratDto> update(
            @PathVariable Long id,
            @RequestBody @Valid ContratDto contratDto,
            @RequestHeader("Authorization") String authorization
    )
    {
        return new ResponseEntity<>(iContratService.update(id, contratDto, authorization), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ContratDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iContratService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iContratService.byId(id), HttpStatus.OK);
    }

    @GetMapping("/agence")
    public ResponseEntity<List<ContratDto>> allByAgence(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iContratService.allByAgence(agenceId, pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}/agence")
    public ResponseEntity<ContratDto> byIdByAgence(
            @PathVariable Long id,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iContratService.byIdAndAgence(id, agenceId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Contrat deleted successfully.");
        iContratService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
