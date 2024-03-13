package com.example.immeubleservice.controller;

import com.example.immeubleservice.dto.ImmeubleDto;
import com.example.immeubleservice.entity.enums.StatusImmeuble;
import com.example.immeubleservice.exception.Error;
import com.example.immeubleservice.service.IimmeubleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/immeubles")
@RequiredArgsConstructor
public class ImmeubleController {

    private final IimmeubleService iimmeubleService;

    @PostMapping
    public ResponseEntity<ImmeubleDto> save(@RequestBody @Valid ImmeubleDto immeubleDto)
    {
        return new ResponseEntity<>(iimmeubleService.save(immeubleDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImmeubleDto> update(
            @PathVariable Long id,
            @RequestBody @Valid ImmeubleDto immeubleDto
    )
    {
        return new ResponseEntity<>(iimmeubleService.update(id, immeubleDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Immeuble deleted successfully.");
        iimmeubleService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImmeubleDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iimmeubleService.byId(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ImmeubleDto>> all()
    {
        return new ResponseEntity<>(iimmeubleService.all(), HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Void> updateStatusImmeuble(
            @PathVariable Long id,
            @RequestParam StatusImmeuble statusImmeuble
    )
    {
        iimmeubleService.updateEtatImmeuble(id, statusImmeuble);
        return ResponseEntity.ok().build();
    }
}
