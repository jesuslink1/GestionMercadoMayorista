package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.cliente.ClienteMeResponseDto;
import com.sise.GestionMercadoMayorista.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cliente")
@SecurityRequirement(name = "bearerAuth")
public class ClienteMeController {

    private final ClienteService clienteService;

    public ClienteMeController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/me")
    public ResponseEntity<ClienteMeResponseDto> me() {
        return ResponseEntity.ok(clienteService.obtenerMe());
    }
}

