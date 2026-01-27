package com.sise.GestionMercadoMayorista.service;

public interface MailService {
    void sendPasswordResetEmail(String toEmail, String resetLink);
}
