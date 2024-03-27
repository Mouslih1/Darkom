package com.example.evenementservice.controller;

import com.example.evenementservice.dto.EvenementDto;
import com.example.evenementservice.exception.Error;
import com.example.evenementservice.service.IEvenementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/evenements")
public class EvenementController {

    private final IEvenementService iEvenementService;

    @PostMapping
    public ResponseEntity<EvenementDto> save(@RequestBody @Valid EvenementDto evenementDto)
    {
        return new ResponseEntity<>(iEvenementService.save(evenementDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EvenementDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iEvenementService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvenementDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iEvenementService.byId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvenementDto> update(@PathVariable Long id, @RequestBody @Valid EvenementDto evenementDto)
    {
        return new ResponseEntity<>(iEvenementService.update(id, evenementDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Evenement deleted successfully.");
        iEvenementService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
