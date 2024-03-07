package com.example.agenceservice.controller;

import com.example.agenceservice.dto.AgenceDto;
import com.example.agenceservice.dto.AgenceRequest;
import com.example.agenceservice.dto.AgenceResponse;
import com.example.agenceservice.exception.Error;
import com.example.agenceservice.service.impl.AgenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping( value = "/{agentCreatedBy}")
    public ResponseEntity<AgenceResponse> save(
            @PathVariable Long agentCreatedBy,
            @ModelAttribute @Valid AgenceRequest agenceRequest
    ) {
        return new ResponseEntity<>(agenceService.save(agentCreatedBy, agenceRequest), HttpStatus.CREATED);
    }

    @PutMapping( "/{agenceId}")
    public ResponseEntity<AgenceDto> update(
            @PathVariable Long agenceId,
            @RequestBody @Valid AgenceDto agenceDto
    ){
        return new ResponseEntity<>(agenceService.update(agenceId, agenceDto), HttpStatus.OK);
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
