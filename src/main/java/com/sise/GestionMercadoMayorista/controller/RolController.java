package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.auth.RolDto;
import com.sise.GestionMercadoMayorista.service.RolService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/roles")
@SecurityRequirement(name = "bearerAuth")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // Solo ADMIN puede gestionar roles
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RolDto>> listarRoles() {
        return ResponseEntity.ok(rolService.listarTodos());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolDto> crearRol(@RequestBody RolDto rolDto) {
        RolDto creado = rolService.crearRol(rolDto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{idRol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolDto> actualizarRol(@PathVariable Integer idRol,
                                                @RequestBody RolDto rolDto) {
        RolDto actualizado = rolService.actualizarRol(idRol, rolDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idRol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer idRol) {
        rolService.eliminarLogico(idRol);
        return ResponseEntity.noContent().build();
    }
}