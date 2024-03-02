package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.exception.Error;
import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;


    @PostMapping("/{id}")
    public ResponseEntity<UserDto> register(@PathVariable("id") Long agentCreatedBy,@RequestBody UserDto userDto)
    {
        return new ResponseEntity<>(userService.save(agentCreatedBy,userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> all()
    {
        List<UserDto> users = userService.all();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto)
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
}
