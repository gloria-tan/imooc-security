package com.billwen.learning.imooc.uaa.rest;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {

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

    @Data
    public static class JsonPayloadDto {
        private String name;
    }
}
