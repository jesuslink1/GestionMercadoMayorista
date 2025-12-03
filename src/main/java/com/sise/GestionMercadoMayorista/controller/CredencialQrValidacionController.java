package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.credencial.ValidacionQrResponse;
import com.sise.GestionMercadoMayorista.service.CredencialQrService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credenciales")
public class CredencialQrValidacionController {

    private final CredencialQrService credencialQrService;

    public CredencialQrValidacionController(CredencialQrService credencialQrService) {
        this.credencialQrService = credencialQrService;
    }

    @GetMapping("/validar/{codigoQr}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<ValidacionQrResponse> validarCredencial(
            @PathVariable String codigoQr) {

        ValidacionQrResponse response = credencialQrService.validarPorCodigo(codigoQr);
        return ResponseEntity.ok(response);
    }
}