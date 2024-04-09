package com.example.plainte.controller;

import com.example.plainte.dto.PlainteDto;
import com.example.plainte.exception.Error;
import com.example.plainte.service.IPlainteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plaintes")
@CrossOrigin("*")
public class PlainteController {

    private final IPlainteService iPlainteService;

    @PostMapping
    public ResponseEntity<PlainteDto> save(
            @RequestBody PlainteDto plainteDto,
            @RequestHeader("agenceId") Long agenceId,
            @RequestHeader("id") Long userCreatedNotification,
            @RequestHeader("Authorization") String authorization
    )
    {
        return new ResponseEntity<>(iPlainteService.save(agenceId,userCreatedNotification ,plainteDto, authorization), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlainteDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iPlainteService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlainteDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iPlainteService.byId(id), HttpStatus.OK);
    }

    @GetMapping("/agence")
    public ResponseEntity<List<PlainteDto>> allByAgence(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iPlainteService.allByAgence(agenceId, pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}/agence")
    public ResponseEntity<PlainteDto> byIdByAgence(
            @PathVariable Long id,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iPlainteService.byIdByAgence(id, agenceId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlainteDto> update(@PathVariable Long id, @RequestBody PlainteDto plainteDto)
    {
        return new ResponseEntity<>(iPlainteService.update(id, plainteDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Plainte deleted successfully.");
        iPlainteService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
