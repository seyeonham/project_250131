package com.project_250131.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtils {

    public static String bcrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        return hashedPassword;
    }
}
