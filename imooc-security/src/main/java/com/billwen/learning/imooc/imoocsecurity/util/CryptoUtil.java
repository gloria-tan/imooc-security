package com.billwen.learning.imooc.imoocsecurity.util;

import org.springframework.stereotype.Component;

import java.util.Random;

public class CryptoUtil {

    public static String randomAlphanumeric(int targetStringLength) {
        int leftLimit = 48;
        int rightLimit = 122;
        var random = new Random();

        return random.ints(leftLimit, rightLimit+1)
                .filter( i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
