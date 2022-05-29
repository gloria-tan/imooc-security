package com.billwen.learning.imooc.imoocsecurity.domain.dto;

import com.billwen.learning.imooc.imoocsecurity.annotation.PasswordMatch;
import com.billwen.learning.imooc.imoocsecurity.annotation.ValidEmail;
import com.billwen.learning.imooc.imoocsecurity.annotation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@PasswordMatch
public class UserDto implements Serializable {

    @NotEmpty
    @Size(min = 4, max = 50, message = "用户名长度在4-50个字符之间")
    private String username;

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    private String matchPassword;

    @NotEmpty
    @ValidEmail
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String mobile;
}
