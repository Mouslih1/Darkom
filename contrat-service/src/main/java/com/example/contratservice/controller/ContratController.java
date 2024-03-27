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
public class ContratController {

    private final IContratService iContratService;

    @PostMapping
    public ResponseEntity<ContratDto> save(@RequestBody @Valid ContratDto contratDto)
    {
        return new ResponseEntity<>(iContratService.save(contratDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratDto> update(@PathVariable Long id, @RequestBody @Valid ContratDto contratDto)
    {
        return new ResponseEntity<>(iContratService.update(id, contratDto), HttpStatus.OK);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Contrat deleted successfully.");
        iContratService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
