package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.categoriaProducto.CategoriaProductoRequest;
import com.sise.GestionMercadoMayorista.dto.categoriaProducto.CategoriaProductoResponse;
import com.sise.GestionMercadoMayorista.entity.CategoriaProducto;
import com.sise.GestionMercadoMayorista.repository.CategoriaProductoRepository;
import com.sise.GestionMercadoMayorista.service.CategoriaProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final CategoriaProductoRepository categoriaProductoRepository;

    public CategoriaProductoServiceImpl(CategoriaProductoRepository categoriaProductoRepository) {
        this.categoriaProductoRepository = categoriaProductoRepository;
    }

    @Override
    public List<CategoriaProductoResponse> listarTodas() {
        return categoriaProductoRepository
                .findByEstadoRegistro(1)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaProductoResponse obtenerPorId(Integer id) {
        CategoriaProducto cat = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de producto no encontrada"));
        return toResponse(cat);
    }

    @Override
    public CategoriaProductoResponse crear(CategoriaProductoRequest request) {
        CategoriaProducto entidad = new CategoriaProducto();
        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());
        entidad.setEstado(request.getEstado() != null ? request.getEstado() : Boolean.TRUE);
        entidad.setEstadoRegistro(1);

        CategoriaProducto guardado = categoriaProductoRepository.save(entidad);
        return toResponse(guardado);
    }

    @Override
    public CategoriaProductoResponse actualizar(Integer id, CategoriaProductoRequest request) {
        CategoriaProducto entidad = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de producto no encontrada"));

        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());
        if (request.getEstado() != null) {
            entidad.setEstado(request.getEstado());
        }

        CategoriaProducto guardado = categoriaProductoRepository.save(entidad);
        return toResponse(guardado);
    }

    @Override
    public void eliminarLogico(Integer id) {
        CategoriaProducto entidad = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de producto no encontrada"));
        entidad.setEstadoRegistro(0);
        entidad.setEstado(false);
        categoriaProductoRepository.save(entidad);
    }

    private CategoriaProductoResponse toResponse(CategoriaProducto cat) {
        CategoriaProductoResponse dto = new CategoriaProductoResponse();
        dto.setId(cat.getId());
        dto.setNombre(cat.getNombre());
        dto.setDescripcion(cat.getDescripcion());
        dto.setEstado(cat.getEstado());
        return dto;
    }
}