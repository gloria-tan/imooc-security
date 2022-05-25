package com.billwen.learning.imooc.imoocsecurity.rest;

import com.billwen.learning.imooc.imoocsecurity.domain.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authorize")
public class AuthorizeResource {

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody UserDto userDto) {
        return userDto;
    }
}
