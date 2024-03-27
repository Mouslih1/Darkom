package com.example.agenceservice.controller;

import com.example.agenceservice.dto.AgenceLogoRequest;
import com.example.agenceservice.dto.AgenceRequest;
import com.example.agenceservice.dto.AgenceResponse;
import com.example.agenceservice.exception.Error;
import com.example.agenceservice.service.impl.AgenceService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/v1/agences")
public class AgenceController {

    private final AgenceService agenceService;

    @PostMapping
    public ResponseEntity<AgenceResponse> save(
            @ModelAttribute @Valid AgenceRequest agenceRequest
    )
    {
        return new ResponseEntity<>(agenceService.save(agenceRequest), HttpStatus.CREATED);
    }

    @PutMapping( "/{agenceId}")
    public ResponseEntity<AgenceResponse> update(
            @PathVariable Long agenceId,
            @ModelAttribute @Valid AgenceRequest agenceRequest
    )
    {
        return new ResponseEntity<>(agenceService.update(agenceId, agenceRequest), HttpStatus.OK);
    }

    @PutMapping( "/logo/{agenceId}")
    public ResponseEntity<AgenceResponse> updateLogo(
            @PathVariable Long agenceId,
            @ModelAttribute @Valid AgenceLogoRequest agenceLogoRequest
    )
    {
        return new ResponseEntity<>(agenceService.updateLogo(agenceId, agenceLogoRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AgenceResponse>> all()
    {
        return new ResponseEntity<>(agenceService.all(), HttpStatus.OK);
    }

    @GetMapping("/{agenceId}")
    public ResponseEntity<AgenceResponse> byId(@PathVariable Long agenceId)
    {
        return new ResponseEntity<>(agenceService.byId(agenceId), HttpStatus.OK);
    }

    @DeleteMapping("/{agenceId}")
    public ResponseEntity<Error> delete(@PathVariable Long agenceId)
    {
        Error error = new Error("Agence deleted successfully.");
        agenceService.delete(agenceId);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
