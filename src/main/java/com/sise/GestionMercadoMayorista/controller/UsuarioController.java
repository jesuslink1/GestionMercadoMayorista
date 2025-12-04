package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.stand.CambiarEstadoUsuarioRequest;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioRequestDto;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioResponseDto;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioUpdateRequest;
import com.sise.GestionMercadoMayorista.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/admin/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar usuarios con filtros opcionales: ?rol=SOCIO&estado=ACTIVO
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String estado
    ) {
        List<UsuarioResponseDto> lista = usuarioService.listarUsuarios(rol, estado);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuario(@PathVariable Integer idUsuario) {
        UsuarioResponseDto dto = usuarioService.obtenerPorId(idUsuario);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // solo admin puede crear usuarios
    public ResponseEntity<UsuarioResponseDto> crearUsuario(@RequestBody UsuarioRequestDto request) {
        UsuarioResponseDto creado = usuarioService.crearUsuario(request);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<UsuarioResponseDto> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioUpdateRequest request
    ) {
        UsuarioResponseDto dto = usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{idUsuario}/estado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer idUsuario,
                                              @RequestBody CambiarEstadoUsuarioRequest request) {
        usuarioService.cambiarEstado(idUsuario, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer idUsuario) {
        usuarioService.eliminarLogico(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
