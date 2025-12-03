package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.usuario.RegistroClienteRequest;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioResponseDto;
import com.sise.GestionMercadoMayorista.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/clientes")
public class ClientePublicController {

    private final UsuarioService usuarioService;

    public ClientePublicController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /api/public/clientes/registro
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDto> registrarCliente(
            @RequestBody RegistroClienteRequest request
    ) {
        UsuarioResponseDto dto = usuarioService.registrarClientePublico(request);
        return ResponseEntity.ok(dto);
    }
}