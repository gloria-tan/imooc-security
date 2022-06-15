package com.billwen.learning.imooc.uaa.rest;

import com.billwen.learning.imooc.uaa.domain.User;
import com.billwen.learning.imooc.uaa.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/principal")
    public Authentication getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World";
    }

    @PostMapping("/greeting")
    @ResponseStatus(HttpStatus.CREATED)
    public String makeGreeting(@RequestParam String name) {
        return "Hello world " + name;
    }

    @PutMapping("/greeting/{name}")
    public String putGreeting(@PathVariable String name) {
        return "Hello world " + name;
    }

    @PostMapping("/jsonpayload")
    public String putGreeting(@RequestBody JsonPayloadDto payloadDto) {
        return "Hello world " + payloadDto.name;
    }

    @GetMapping("/users/{username}")
    public String getCurrentUsername(@PathVariable String username) {
        return "hello, " + username;
    }

    @GetMapping("/users/manager/{message}")
    public String getManager(@PathVariable String message) {
        return "Hello, " + message;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/by-email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findOptionalByEmail(email).orElseThrow();
    }

    @Data
    public static class JsonPayloadDto {
        private String name;
    }
}
