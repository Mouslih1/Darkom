package com.example.userservice.controllers;

import com.example.userservice.dtos.UserRequest;
import com.example.userservice.dtos.UserRequestLogo;
import com.example.userservice.dtos.UserResponse;
import com.example.userservice.exceptions.Error;
import com.example.userservice.services.IUserService;
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

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestHeader("agenceId") Long agenceId ,
            @ModelAttribute @Valid UserRequest userRequest
    )
    {
        return new ResponseEntity<>(userService.save(agenceId, userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserResponse> registerByAdmin(
            @RequestBody @Valid UserRequest userRequest
    )
    {
        return new ResponseEntity<>(userService.saveByAdmin(userRequest), HttpStatus.CREATED);
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
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest)
    {
        return new ResponseEntity<>(userService.update(id, userRequest), HttpStatus.OK);
    }

    @PutMapping("/logo/{id}")
    public ResponseEntity<UserResponse> updateLogo(@PathVariable Long id, @ModelAttribute @Valid UserRequestLogo userRequestLogo)
    {
        return new ResponseEntity<>(userService.updateLogo(id, userRequestLogo), HttpStatus.OK);
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
