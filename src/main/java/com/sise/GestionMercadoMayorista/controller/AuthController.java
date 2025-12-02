package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.common.ApiError;
import com.sise.GestionMercadoMayorista.dto.auth.AuthRequest;
import com.sise.GestionMercadoMayorista.dto.auth.AuthResponse;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.security.CustomUserDetails;
import com.sise.GestionMercadoMayorista.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

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
            String rol = userDetails.getRolNombre(); // ADMIN, SOCIO, etc.

            String token = jwtUtil.generateToken(email, rol);

            AuthResponse response = new AuthResponse(token, email, rol);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            // Cubre BadCredentialsException, UsernameNotFoundException, etc.
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
}
