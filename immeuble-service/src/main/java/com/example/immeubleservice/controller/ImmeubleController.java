package com.example.immeubleservice.controller;

import com.example.immeubleservice.dto.ImmeubleDto;
import com.example.immeubleservice.entity.enums.StatusImmeuble;
import com.example.immeubleservice.exception.Error;
import com.example.immeubleservice.service.IimmeubleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/immeubles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ImmeubleController {

    private final IimmeubleService iimmeubleService;

    @PostMapping
    public ResponseEntity<ImmeubleDto> save(
            @RequestHeader("agenceId") Long agenceId,
            @RequestBody @Valid ImmeubleDto immeubleDto
    )
    {
        return new ResponseEntity<>(iimmeubleService.save(agenceId,immeubleDto), HttpStatus.CREATED);
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
    public ResponseEntity<List<ImmeubleDto>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(iimmeubleService.all(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}/agence")
    public ResponseEntity<ImmeubleDto> byIdByAgence(
            @PathVariable Long id,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iimmeubleService.byIdAndAgence(id, agenceId), HttpStatus.OK);
    }

    @GetMapping("/agence")
    public ResponseEntity<List<ImmeubleDto>> allByAgence(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(iimmeubleService.allByAgence(agenceId, pageNo, pageSize), HttpStatus.OK);
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
