package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.common.ApiError;
import com.sise.GestionMercadoMayorista.dto.auth.AuthRequest;
import com.sise.GestionMercadoMayorista.dto.auth.AuthResponse;
import com.sise.GestionMercadoMayorista.dto.auth.ForgotPasswordRequest;
import com.sise.GestionMercadoMayorista.dto.auth.ResetPasswordRequest;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.security.CustomUserDetails;
import com.sise.GestionMercadoMayorista.security.JwtUtil;
import com.sise.GestionMercadoMayorista.service.MailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    // ✅ NUEVO
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioRepository usuarioRepository,
                          MailService mailService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // LOGIN (NO TOCAR)
    // =========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request,
                                   HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String email = userDetails.getUsername();
            String rol = userDetails.getRolNombre();

            String token = jwtUtil.generateToken(email, rol);
            String nombreCompleto = null;
            String fotoUrl = null;

            Optional<Usuario> opt = usuarioRepository.findByEmail(email);
            if (opt.isPresent()) {
                Usuario u = opt.get();

                String nombres = u.getNombres() != null ? u.getNombres().trim() : "";
                String apellidos = u.getApellidos() != null ? u.getApellidos().trim() : "";
                String full = (nombres + " " + apellidos).trim();

                nombreCompleto = full.isEmpty() ? null : full;
                fotoUrl = u.getFotoUrl();
            }

            AuthResponse response = new AuthResponse(token, email, rol, nombreCompleto, fotoUrl);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            ApiError apiError = new ApiError(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "Credenciales inválidas. Verifique su email y contraseña.",
                    httpRequest.getRequestURI()
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(apiError);
        }
    }

    // =========================
    // FORGOT PASSWORD (PUBLICO)
    // =========================
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : "";

        // ✅ No revelar si el correo existe
        String genericMsg = "Si el correo está registrado, recibirás un enlace para restablecer tu contraseña.";

        if (email.isBlank()) return ResponseEntity.ok(genericMsg);

        Optional<Usuario> opt = usuarioRepository.findByEmail(email);
        if (opt.isEmpty()) return ResponseEntity.ok(genericMsg);

        Usuario u = opt.get();
        if (u.getEstadoRegistro() == null || u.getEstadoRegistro() != 1) {
            return ResponseEntity.ok(genericMsg);
        }

        // ✅ USAR TU MÉTODO REAL
        String resetToken = jwtUtil.generatePasswordResetToken(email);

        String resetLink = frontendBaseUrl + "/reset-password?token=" + resetToken;

        mailService.sendPasswordResetEmail(email, resetLink);

        return ResponseEntity.ok(genericMsg);
    }

    // =========================
    // RESET PASSWORD (PUBLICO)
    // =========================
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request,
                                           HttpServletRequest httpRequest) {

        String token = request.getToken() != null ? request.getToken().trim() : "";
        String newPassword = request.getNewPassword() != null ? request.getNewPassword().trim() : "";

        if (token.isBlank()) {
            ApiError apiError = new ApiError(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Token requerido.",
                    httpRequest.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }

        if (newPassword.length() < 6) {
            ApiError apiError = new ApiError(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "La contraseña debe tener mínimo 6 caracteres.",
                    httpRequest.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }

        // ✅ VALIDAR PURPOSE + EXPIRACIÓN (TU MÉTODO YA HACE TODO ESO)
        final String email;
        try {
            email = jwtUtil.validatePasswordResetTokenAndGetEmail(token);
        } catch (ExpiredJwtException e) {
            ApiError apiError = new ApiError(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "Token expirado.",
                    httpRequest.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        } catch (JwtException e) {
            ApiError apiError = new ApiError(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "Token inválido.",
                    httpRequest.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }

        Optional<Usuario> opt = usuarioRepository.findByEmail(email);
        if (opt.isEmpty()) {
            ApiError apiError = new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Usuario no encontrado.",
                    httpRequest.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }

        Usuario u = opt.get();
        u.setPasswordHash(passwordEncoder.encode(newPassword));
        usuarioRepository.save(u);

        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}