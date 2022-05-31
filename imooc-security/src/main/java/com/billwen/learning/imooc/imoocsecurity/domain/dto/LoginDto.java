package com.billwen.learning.imooc.imoocsecurity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto implements Serializable {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
