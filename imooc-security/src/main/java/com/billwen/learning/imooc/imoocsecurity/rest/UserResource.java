package com.billwen.learning.imooc.imoocsecurity.rest;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World";
    }

    @PostMapping("/greeting")
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

    @Data
    public static class JsonPayloadDto {
        private String name;
    }
}
