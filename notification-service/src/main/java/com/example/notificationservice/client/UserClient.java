package com.example.notificationservice.client;

import com.example.notificationservice.dtos.UserResponse;
import com.example.notificationservice.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(url = "http://localhost:8085/api/v1/users",name = "users")
public interface UserClient {

    @GetMapping("/agence/feign/{agenceId}")
    ResponseEntity<List<UserResponse>> getUsersByAgenceForNotifie(
            @PathVariable Long agenceId,
            @RequestHeader("Authorization") String authorization
    );

    @GetMapping("/feign/{id}")
    ResponseEntity<UserResponse> byIdFeign(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorization
    );

    @GetMapping("/feign")
    ResponseEntity<User> byUsername(
            @RequestParam String username,
            @RequestHeader("Authorization") String authorization
    );
}
