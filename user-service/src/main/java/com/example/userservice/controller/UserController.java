package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.Error;
import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    //TODO: INCLUDE THE ID OF AGENCE
    @PostMapping("/{id}")
    public ResponseEntity<UserResponse> register(
            /*@RequestHeader("agenceId") Long agenceId ,*/
            @PathVariable("id") Long agentCreatedBy,
            @RequestBody @Valid UserRequest userRequest
    )
    {
        return new ResponseEntity<>(userService.save(agentCreatedBy,userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<UserResponse> registerByAdmin(
            /*@RequestHeader("agenceId") Long agenceId ,*/
            @PathVariable("id") Long agentCreatedBy,
            @RequestBody @Valid UserRequest userRequest
    )
    {
        return new ResponseEntity<>(userService.saveByAdmin(agentCreatedBy,userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> all()
    {
        List<UserResponse> users = userService.all();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto)
    {
        return new ResponseEntity<>(userService.update(id, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("User deleted successfully.");
        userService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @GetMapping("/agence/{agenceId}")
    public ResponseEntity<List<UserResponse>> allByAgence(@PathVariable Long agenceId)
    {
        List<UserResponse> users = userService.allByAgence(agenceId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}/agence/{agenceId}")
    public ResponseEntity<UserResponse> byIdAndAgence(@PathVariable Long userId, @PathVariable Long agenceId)
    {
        return new ResponseEntity<>(userService.byIdAndAgence(userId, agenceId), HttpStatus.OK);
    }
}
