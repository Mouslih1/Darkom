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
public class PlainteController {

    private final IPlainteService iPlainteService;

    @PostMapping
    public ResponseEntity<PlainteDto> save(@RequestBody PlainteDto plainteDto)
    {
        return new ResponseEntity<>(iPlainteService.save(plainteDto), HttpStatus.CREATED);
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
