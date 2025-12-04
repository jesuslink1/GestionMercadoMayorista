package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.credencial.CrearCredencialRequest;
import com.sise.GestionMercadoMayorista.dto.credencial.CredencialResponse;
import com.sise.GestionMercadoMayorista.service.CredencialQrService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/credenciales-qr")
@SecurityRequirement(name = "bearerAuth")
public class CredencialesQrAdminController {

    private final CredencialQrService credencialQrService;

    public CredencialesQrAdminController(CredencialQrService credencialQrService) {
        this.credencialQrService = credencialQrService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<CredencialResponse> crearCredencial(
            @Valid @RequestBody CrearCredencialRequest request) {

        CredencialResponse response = credencialQrService.crearCredencial(request);
        return ResponseEntity.ok(response);
    }
}