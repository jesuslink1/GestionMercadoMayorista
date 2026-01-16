// com.sise.GestionMercadoMayorista.service.impl.DirectorioProductoServiceImpl

package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoPublicResponseDto;
import com.sise.GestionMercadoMayorista.entity.Producto;
import com.sise.GestionMercadoMayorista.repository.ProductoRepository;
import com.sise.GestionMercadoMayorista.service.DirectorioProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectorioProductoServiceImpl implements DirectorioProductoService {

    private final ProductoRepository productoRepository;

    public DirectorioProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // ================== LISTADO / BÚSQUEDA ==================
    @Override
    public Page<ProductoPublicResponseDto> buscarProductos(
            String nombre,
            Integer idCategoriaProducto,
            String bloque,
            Pageable pageable
    ) {
        String nombreFiltro = (nombre == null || nombre.isBlank())
                ? null
                : "%" + nombre.toLowerCase() + "%";

        Page<Producto> page = productoRepository.buscarPublico(
                nombreFiltro,
                idCategoriaProducto,
                bloque,
                pageable
        );

        return page.map(this::mapToPublicDto);
    }

    // ================== DETALLE POR ID ==================
    @Override
    public ProductoPublicResponseDto obtenerProductoPorId(Integer id) {
        return productoRepository.buscarPublicoPorId(id)
                .map(this::mapToPublicDto)
                .orElse(null);
    }

    // ================== LISTAR POR STAND (PÚBLICO) ==================
    @Override
    public List<ProductoPublicResponseDto> listarPorStandPublico(Integer idStand) {
        return productoRepository.buscarPublicoPorStand(idStand)
                .stream()
                .map(this::mapToPublicDto)
                .collect(Collectors.toList());
    }

    // ================== MAPPER ENTIDAD -> DTO PÚBLICO ==================
    private ProductoPublicResponseDto mapToPublicDto(Producto p) {
        ProductoPublicResponseDto dto = new ProductoPublicResponseDto();

        dto.setIdProducto(p.getId());
        dto.setNombreProducto(p.getNombre());
        dto.setUnidadMedida(p.getUnidadMedida());
        dto.setPrecioActual(p.getPrecioActual());
        dto.setEnOferta(p.getEnOferta());
        dto.setPrecioOferta(p.getPrecioOferta());
        dto.setImagenUrl(p.getImagenUrl());
        dto.setDescripcion(p.getDescripcion());


        // Datos del stand
        if (p.getStand() != null) {
            dto.setIdStand(p.getStand().getId());
            dto.setNombreStand(p.getStand().getNombreComercial());
            dto.setBloque(p.getStand().getBloque());
            dto.setNumeroStand(p.getStand().getNumeroStand());

            if (p.getStand().getCategoriaStand() != null) {
                dto.setCategoriaStand(
                        p.getStand().getCategoriaStand().getNombre()
                );
            }
        }

        // Datos de la categoría de producto
        if (p.getCategoriaProducto() != null) {
            dto.setCategoriaProducto(p.getCategoriaProducto().getNombre());
        }

        return dto;
    }
}
