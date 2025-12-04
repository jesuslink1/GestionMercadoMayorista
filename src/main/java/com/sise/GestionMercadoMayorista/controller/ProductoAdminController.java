package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoResponseDto;
import com.sise.GestionMercadoMayorista.service.ProductoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/productos")
@SecurityRequirement(name = "bearerAuth")
public class ProductoAdminController {

    private final ProductoService productoService;

    public ProductoAdminController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 1) Listar productos con filtros (ADMIN / SUPERVISOR)
    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> listarProductosAuditoria(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer idCategoriaProducto,
            @RequestParam(required = false) String bloque,
            @RequestParam(required = false) Boolean visible,
            @RequestParam(required = false) Integer estadoRegistro
    ) {
        List<ProductoResponseDto> lista = productoService.listarProductosParaAuditoria(
                nombre,
                idCategoriaProducto,
                bloque,
                visible,
                estadoRegistro
        );
        return ResponseEntity.ok(lista);
    }

    // 2) Ver detalle de un producto
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoResponseDto> obtenerDetalle(@PathVariable Integer idProducto) {
        ProductoResponseDto dto = productoService.obtenerProductoPorId(idProducto);
        return ResponseEntity.ok(dto);
    }

    // 3) Ocultar / mostrar en directorio (reutiliza lógica existente)
    @PatchMapping("/{idProducto}/visibilidad")
    public ResponseEntity<Void> cambiarVisibilidadAdmin(
            @PathVariable Integer idProducto,
            @RequestParam boolean visible
    ) {
        productoService.cambiarVisibilidad(idProducto, visible);
        return ResponseEntity.noContent().build();
    }

    // 4) Eliminar lógico (forzar desde ADMIN/SUPERVISOR)
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> eliminarLogicoAdmin(@PathVariable Integer idProducto) {
        productoService.eliminarLogico(idProducto);
        return ResponseEntity.noContent().build();
    }
}