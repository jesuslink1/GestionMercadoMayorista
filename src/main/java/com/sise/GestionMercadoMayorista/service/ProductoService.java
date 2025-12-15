package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoPublicResponseDto;
import com.sise.GestionMercadoMayorista.dto.producto.ProductoRequestDto;
import com.sise.GestionMercadoMayorista.dto.producto.ProductoResponseDto;

import java.util.List;

public interface ProductoService {

    // SOCIO
    ProductoResponseDto crearProductoEnMiStand(Integer idStand, ProductoRequestDto dto);

    ProductoResponseDto actualizarProducto(Integer idProducto, ProductoRequestDto dto);

    void cambiarVisibilidad(Integer idProducto, boolean visible);

    void eliminarLogico(Integer idProducto);

    List<ProductoResponseDto> listarProductosDeMiStand(Integer idStand);

    // ADMIN / SUPERVISOR ===

    /**
     * Listado para auditoría con filtros opcionales.
     */
    List<ProductoResponseDto> listarProductosParaAuditoria(
            String nombre,
            Integer idCategoriaProducto,
            String bloque,
            Boolean visible,
            Integer estadoRegistro
    );

    /**
     * Ver detalle de producto (ADMIN / SUPERVISOR).
     */
    ProductoResponseDto obtenerProductoPorId(Integer idProducto);

    List<ProductoPublicResponseDto> listarPorStandPublico(Integer idStand);
}
