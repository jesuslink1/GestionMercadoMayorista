package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoPublicResponseDto;
import com.sise.GestionMercadoMayorista.service.DirectorioProductoService;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/productos")
public class DirectorioProductoController {

    private final DirectorioProductoService directorioProductoService;

    public DirectorioProductoController(DirectorioProductoService directorioProductoService) {
        this.directorioProductoService = directorioProductoService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ProductoPublicResponseDto>> buscarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer idCategoriaProducto,
            @RequestParam(required = false) String bloque,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductoPublicResponseDto> resultado =
                directorioProductoService.buscarProductos(nombre, idCategoriaProducto, bloque, pageable);

        return ResponseEntity.ok(resultado);
    }
}