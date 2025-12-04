package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.favorito.FavoritoResponseDto;
import com.sise.GestionMercadoMayorista.service.FavoritoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente/favoritos")
@SecurityRequirement(name = "bearerAuth")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    // Agregar a favoritos
    @PostMapping("/{idStand}")
    public ResponseEntity<Void> agregar(@PathVariable Integer idStand) {
        favoritoService.agregarAFavoritos(idStand);
        return ResponseEntity.ok().build();
    }

    // Quitar de favoritos
    @DeleteMapping("/{idStand}")
    public ResponseEntity<Void> quitar(@PathVariable Integer idStand) {
        favoritoService.quitarDeFavoritos(idStand);
        return ResponseEntity.noContent().build();
    }

    // Listar mis favoritos
    @GetMapping
    public ResponseEntity<List<FavoritoResponseDto>> listar() {
        List<FavoritoResponseDto> lista = favoritoService.listarMisFavoritos();
        return ResponseEntity.ok(lista);
    }
}
