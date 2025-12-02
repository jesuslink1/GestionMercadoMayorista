package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoPublicResponseDto;
import com.sise.GestionMercadoMayorista.entity.Producto;
import com.sise.GestionMercadoMayorista.repository.ProductoRepository;
import com.sise.GestionMercadoMayorista.service.DirectorioProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DirectorioProductoServiceImpl implements DirectorioProductoService {

    private final ProductoRepository productoRepository;

    public DirectorioProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Page<ProductoPublicResponseDto> buscarProductos(
            String nombre,
            Integer idCategoriaProducto,
            String bloque,
            Pageable pageable
    ) {
        String patronNombre = null;
        if (nombre != null) {
            nombre = nombre.trim();
            if (!nombre.isEmpty()) {
                // lo pasamos a minúsculas y le agregamos % para búsqueda parcial
                patronNombre = "%" + nombre.toLowerCase() + "%";
            }
        }

        Page<Producto> page = productoRepository.buscarPublico(
                patronNombre,
                idCategoriaProducto,
                bloque,
                pageable
        );

        return page.map(this::toPublicDto);
    }

    private ProductoPublicResponseDto toPublicDto(Producto p) {
        ProductoPublicResponseDto dto = new ProductoPublicResponseDto();

        dto.setIdProducto(p.getId());
        dto.setNombreProducto(p.getNombre());
        dto.setUnidadMedida(p.getUnidadMedida());
        dto.setPrecioActual(p.getPrecioActual());
        dto.setEnOferta(p.getEnOferta());
        dto.setPrecioOferta(p.getPrecioOferta());
        dto.setImagenUrl(p.getImagenUrl());

        // Datos del stand
        if (p.getStand() != null) {
            dto.setNombreStand(p.getStand().getNombreComercial());
            dto.setBloque(p.getStand().getBloque());
            dto.setNumeroStand(p.getStand().getNumeroStand());

            if (p.getStand().getCategoriaStand() != null) {
                dto.setCategoriaStand(p.getStand().getCategoriaStand().getNombre());
            }
        }

        // Datos de la categoría de producto
        if (p.getCategoriaProducto() != null) {
            dto.setCategoriaProducto(p.getCategoriaProducto().getNombre());
        }

        return dto;
    }
}
