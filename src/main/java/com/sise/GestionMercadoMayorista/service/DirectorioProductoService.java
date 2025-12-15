package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoPublicResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DirectorioProductoService {

    Page<ProductoPublicResponseDto> buscarProductos(
            String nombre,
            Integer idCategoriaProducto,
            String bloque,
            Pageable pageable
    );

    // 👇 NUEVO: obtener detalle de un producto público
    ProductoPublicResponseDto obtenerProductoPorId(Integer id);

    List<ProductoPublicResponseDto> listarPorStandPublico(Integer idStand);
}
