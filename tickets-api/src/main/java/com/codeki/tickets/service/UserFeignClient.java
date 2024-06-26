package com.codeki.tickets.service;

import com.codeki.tickets.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "auth-service")
public interface UserFeignClient {

    @GetMapping("/users")
    ResponseEntity<List<UserDto>> getAllUsers();

    @GetMapping("/users/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id);

}
