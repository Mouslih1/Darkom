package com.example.travauxservice.controller;

import com.example.travauxservice.dto.TravauxDto;
import com.example.travauxservice.exception.Error;
import com.example.travauxservice.service.ITravauxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/travaux")
@RequiredArgsConstructor
public class TravauxController {

    private final ITravauxService iTravauxService;

    @PostMapping
    public ResponseEntity<TravauxDto> save(@RequestBody @Valid TravauxDto travauxDto)
    {
        return new ResponseEntity<>(iTravauxService.save(travauxDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TravauxDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iTravauxService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravauxDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iTravauxService.byId(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravauxDto> update(@PathVariable Long id, @RequestBody @Valid TravauxDto travauxDto)
    {
        return new ResponseEntity<>(iTravauxService.update(id, travauxDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Travaux deleted successfully.");
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
