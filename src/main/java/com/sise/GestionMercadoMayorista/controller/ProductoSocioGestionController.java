package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoRequestDto;
import com.sise.GestionMercadoMayorista.dto.producto.ProductoResponseDto;
import com.sise.GestionMercadoMayorista.service.ProductoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/socio/productos")
public class ProductoSocioGestionController {

    private final ProductoService productoService;

    public ProductoSocioGestionController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoResponseDto> actualizarProducto(
            @PathVariable Integer idProducto,
            @RequestBody ProductoRequestDto dto
    ) {
        ProductoResponseDto actualizado = productoService.actualizarProducto(idProducto, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{idProducto}/visibilidad")
    public ResponseEntity<Void> cambiarVisibilidad(
            @PathVariable Integer idProducto,
            @RequestParam boolean visible
    ) {
        productoService.cambiarVisibilidad(idProducto, visible);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer idProducto) {
        productoService.eliminarLogico(idProducto);
        return ResponseEntity.noContent().build();
    }
}
