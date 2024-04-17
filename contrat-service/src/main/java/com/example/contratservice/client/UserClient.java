package com.example.contratservice.client;

import com.example.contratservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://localhost:8085/api/v1/users",name = "users")
public interface UserClient {

    @GetMapping("/feign/{id}")
    ResponseEntity<UserResponse> byIdFeign(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorization
    );
}
