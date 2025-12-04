package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.credencial.CredencialResponse;
import com.sise.GestionMercadoMayorista.service.CredencialQrService;
import com.sise.GestionMercadoMayorista.security.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/socio/credencial-qr")
@SecurityRequirement(name = "bearerAuth")
public class CredencialQrSocioController {

    private final CredencialQrService credencialQrService;

    public CredencialQrSocioController(CredencialQrService credencialQrService) {
        this.credencialQrService = credencialQrService;
    }

    @GetMapping
    @PreAuthorize("hasRole('SOCIO')")
    public ResponseEntity<CredencialResponse> obtenerMiCredencial() {
        String emailActual = SecurityUtils.getCurrentUserEmail();
        CredencialResponse response = credencialQrService.obtenerMiCredencialPorEmail(emailActual);
        return ResponseEntity.ok(response);
    }
}
