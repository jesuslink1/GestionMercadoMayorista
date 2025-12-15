package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoPublicResponseDto;
import com.sise.GestionMercadoMayorista.service.DirectorioProductoService;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/productos")
public class DirectorioProductoController {

    private final DirectorioProductoService directorioProductoService;

    public DirectorioProductoController(DirectorioProductoService directorioProductoService) {
        this.directorioProductoService = directorioProductoService;
    }

    // ===== LISTADO / BUSQUEDA =====
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
                directorioProductoService.buscarProductos(
                        nombre,
                        idCategoriaProducto,
                        bloque,
                        pageable
                );

        return ResponseEntity.ok(resultado);
    }

    // ===== DETALLE POR ID (PARA VISTAPRODUCTO) =====
    @GetMapping("/{id}")
    public ResponseEntity<ProductoPublicResponseDto> obtenerProductoPorId(
            @PathVariable Integer id
    ) {
        ProductoPublicResponseDto dto = directorioProductoService.obtenerProductoPorId(id);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/por-stand/{idStand}")
    public ResponseEntity<List<ProductoPublicResponseDto>> listarPorStand(
            @PathVariable Integer idStand
    ) {
        List<ProductoPublicResponseDto> lista =
                directorioProductoService.listarPorStandPublico(idStand);
        return ResponseEntity.ok(lista);
    }
}
