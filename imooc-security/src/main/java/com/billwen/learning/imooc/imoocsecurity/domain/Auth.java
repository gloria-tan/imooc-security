package com.billwen.learning.imooc.imoocsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Auth implements Serializable {

    private String accessToken;

    private String refreshToken;
}
