package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Restablecer contraseña - Mercado Mayorista");

            String html = buildHtml(resetLink);

            helper.setText(html, true); // true = HTML
            mailSender.send(message);

        } catch (MessagingException e) {
            // Aquí puedes lanzar RuntimeException o loguear
            throw new RuntimeException("No se pudo enviar el correo de recuperación", e);
        }
    }

    private String buildHtml(String resetLink) {
        return """
        <div style="
            font-family: 'Segoe UI', Roboto, Arial, sans-serif;
            background: linear-gradient(135deg, #ecfdf5, #d1fae5);
            padding: 40px 16px;
        ">
          <div style="
              max-width: 520px;
              margin: 0 auto;
              background: #ffffff;
              border-radius: 18px;
              padding: 32px;
              box-shadow: 0 25px 50px -12px rgba(0,0,0,0.15);
              border: 1px solid rgba(34,197,94,0.25);
          ">

            <!-- Logo / Icono -->
            <div style="text-align: center; margin-bottom: 24px;">
              <div style="
                  width: 72px;
                  height: 72px;
                  margin: 0 auto;
                  border-radius: 18px;
                  background: linear-gradient(135deg, #22c55e, #16a34a);
                  display: flex;
                  align-items: center;
                  justify-content: center;
                  color: white;
                  font-size: 34px;
                  font-weight: bold;
                  box-shadow: 0 10px 25px rgba(34,197,94,0.4);
              ">
                🔒
              </div>
            </div>

            <!-- Título -->
            <h2 style="
                text-align: center;
                color: #065f46;
                margin-bottom: 12px;
                font-size: 22px;
            ">
              Restablecer contraseña
            </h2>

            <!-- Texto -->
            <p style="
                color: #374151;
                font-size: 15px;
                text-align: center;
                margin-bottom: 24px;
            ">
              Se solicitó el restablecimiento de tu contraseña en el
              <strong>Mercado Mayorista de Santa Anita</strong>.
            </p>

            <!-- Botón -->
            <div style="text-align: center; margin: 32px 0;">
              <a href="%s"
                 style="
                   display: inline-block;
                   padding: 14px 26px;
                   background: linear-gradient(135deg, #22c55e, #16a34a);
                   color: #ffffff;
                   font-weight: 700;
                   border-radius: 999px;
                   text-decoration: none;
                   font-size: 15px;
                   box-shadow: 0 10px 25px rgba(34,197,94,0.45);
                 ">
                Restablecer contraseña
              </a>
            </div>

            <!-- Avisos -->
            <p style="
                color: #6b7280;
                font-size: 13px;
                text-align: center;
                margin-bottom: 8px;
            ">
              Este enlace es válido por unos minutos por motivos de seguridad.
            </p>

            <p style="
                color: #9ca3af;
                font-size: 12px;
                text-align: center;
                margin-bottom: 0;
            ">
              Si no solicitaste este cambio, puedes ignorar este correo con total seguridad.
            </p>

            <!-- Footer -->
            <div style="
                margin-top: 32px;
                padding-top: 16px;
                border-top: 1px solid #e5e7eb;
                text-align: center;
                font-size: 12px;
                color: #9ca3af;
            ">
              Mercado Mayorista de Santa Anita<br/>
              Sistema de gestión y directorio
            </div>

          </div>
        </div>
        """.formatted(resetLink);
    }
}