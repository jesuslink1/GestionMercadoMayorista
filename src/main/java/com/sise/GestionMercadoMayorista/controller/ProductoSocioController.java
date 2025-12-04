package com.sise.GestionMercadoMayorista.controller;

import java.util.List;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoRequestDto;
import com.sise.GestionMercadoMayorista.dto.producto.ProductoResponseDto;
import com.sise.GestionMercadoMayorista.service.ProductoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/socio/stands/{idStand}/productos")
@SecurityRequirement(name = "bearerAuth")
public class ProductoSocioController {

    private final ProductoService productoService;

    public ProductoSocioController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(
            @PathVariable Integer idStand,
            @RequestBody ProductoRequestDto dto
    ) {
        ProductoResponseDto creado = productoService.crearProductoEnMiStand(idStand, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> listarProductos(
            @PathVariable Integer idStand
    ) {
        List<ProductoResponseDto> lista = productoService.listarProductosDeMiStand(idStand);
        return ResponseEntity.ok(lista);
    }
}
