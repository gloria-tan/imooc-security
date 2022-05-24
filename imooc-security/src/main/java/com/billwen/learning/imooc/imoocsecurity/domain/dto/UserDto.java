package com.billwen.learning.imooc.imoocsecurity.domain.dto;

import com.billwen.learning.imooc.imoocsecurity.annotation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    @NotEmpty
    @Size(min = 4, max = 50, message = "用户名长度在4-50个字符之间")
    private String username;

    @NotEmpty
    @Size(min = 8, max = 20, message = "密码长度必须在4-20个字符之间")
    private String password;

    private String matchPassword;

    @NotEmpty
    @ValidEmail
    private String email;

    @NotEmpty
    private String name;
}
