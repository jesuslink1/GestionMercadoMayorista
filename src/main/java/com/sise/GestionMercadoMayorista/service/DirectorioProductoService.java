package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoPublicResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DirectorioProductoService {

    Page<ProductoPublicResponseDto> buscarProductos(
            String nombre,
            Integer idCategoriaProducto,
            String bloque,
            Pageable pageable
    );
}
