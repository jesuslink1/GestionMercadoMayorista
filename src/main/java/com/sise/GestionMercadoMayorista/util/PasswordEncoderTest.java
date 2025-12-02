package com.sise.GestionMercadoMayorista.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encoded = encoder.encode(rawPassword);

        System.out.println("Password en texto plano : " + rawPassword);
        System.out.println("Password encriptado    : " + encoded);
    }
}