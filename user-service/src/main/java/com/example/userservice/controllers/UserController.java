package com.example.userservice.controllers;

import com.example.userservice.dtos.UserPasswordDto;
import com.example.userservice.dtos.UserRequest;
import com.example.userservice.dtos.UserRequestLogo;
import com.example.userservice.dtos.UserResponse;
import com.example.userservice.entities.User;
import com.example.userservice.exceptions.Error;
import com.example.userservice.services.IUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestHeader("agenceId") Long agenceId ,
            @ModelAttribute @Valid UserRequest userRequest
    )
    {
        System.out.println();
        return new ResponseEntity<>(userService.save(agenceId, userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserResponse> registerByAdmin(
            @ModelAttribute @Valid UserRequest userRequest
    )
    {
        return new ResponseEntity<>(userService.saveByAdmin(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> all(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>( userService.all(pageNo, pageSize), HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/feign/{id}")
    public ResponseEntity<UserResponse> byIdFeign(@PathVariable Long id, @RequestHeader("Authorization") String authorization)
    {
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequest userRequest
    )
    {
        return new ResponseEntity<>(userService.update(id, userRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}/admin")
    public ResponseEntity<UserResponse> updateByAdmin(
            @PathVariable Long id,
            @RequestBody @Valid UserRequest userRequest
    )
    {
        return new ResponseEntity<>(userService.updateByAdmin(id, userRequest), HttpStatus.OK);
    }

    @PutMapping("/logo/{id}")
    public ResponseEntity<UserResponse> updatePhotoProfil(
            @PathVariable Long id,
            @ModelAttribute @Valid UserRequestLogo userRequestLogo
    )
    {
        return new ResponseEntity<>(userService.updatePhotoProfil(id, userRequestLogo), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("User deleted successfully.");
        userService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @GetMapping("/agence")
    public ResponseEntity<List<UserResponse>> allByAgence(
            @RequestHeader("agenceId") Long agenceId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return new ResponseEntity<>(userService.allByAgence(agenceId, pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/agence/feign/{agenceId}")
    public ResponseEntity<List<UserResponse>> getUsersByAgenceForNotifie(
            @PathVariable Long agenceId,
            @RequestHeader("Authorization") String authorization

    )
    {
        return new ResponseEntity<>(userService.getUsersByAgenceForNotifie(agenceId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/agence")
    public ResponseEntity<UserResponse> byIdAndAgence(
            @PathVariable Long userId,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(userService.byIdAndAgence(userId, agenceId), HttpStatus.OK);
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<Error> updatePassword(
            @PathVariable Long id,
            @RequestBody @Valid UserPasswordDto userPasswordDto
    )
    {
        boolean updatedPassword = userService.updatePassword(id, userPasswordDto);

        if(updatedPassword)
        {
            return new ResponseEntity<>(new Error("Password updated successfully."), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Error("Password not updated you have problem."), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logged")
    public ResponseEntity<UserResponse> profile(
            @RequestHeader("id") Long id,
            @RequestHeader("agenceId") Long agenceId
    )
    {
        return new ResponseEntity<>(userService.byIdAndAgence(id, agenceId), HttpStatus.OK);
    }

    @GetMapping("/feign")
    public ResponseEntity<User> byUsername(
            @RequestParam String username,
            @RequestHeader("Authorization") String authorization
    )
    {
        return new ResponseEntity<>(userService.byUsername(username), HttpStatus.OK);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<Error> forgotPassword(@RequestParam String email) throws MessagingException
    {
        userService.forgotPassword(email);
        return new ResponseEntity<>(new Error("Check you email for set password"), HttpStatus.OK);
    }

    @PutMapping("set-password")
    public ResponseEntity<Error> setPassword(@RequestParam String email, @RequestHeader String newPassword)
    {
        userService.setPassword(email, newPassword);
        return new ResponseEntity<>(new Error("New password set successfully login with new password"), HttpStatus.OK);
    }
}
