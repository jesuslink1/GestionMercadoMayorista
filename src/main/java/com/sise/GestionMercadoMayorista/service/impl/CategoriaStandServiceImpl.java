package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.categoriaStand.CategoriaStandResponse;
import com.sise.GestionMercadoMayorista.entity.CategoriaStand;
import com.sise.GestionMercadoMayorista.repository.CategoriaStandRepository;
import com.sise.GestionMercadoMayorista.service.CategoriaStandService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.sise.GestionMercadoMayorista.dto.categoriaStand.CategoriaStandRequest;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaStandServiceImpl implements CategoriaStandService {

    private final CategoriaStandRepository categoriaStandRepository;

    public CategoriaStandServiceImpl(CategoriaStandRepository categoriaStandRepository) {
        this.categoriaStandRepository = categoriaStandRepository;
    }

    @Override
    public List<CategoriaStandResponse> listarTodas() {
        return categoriaStandRepository
                .findByEstadoRegistro(1)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaStandResponse obtenerPorId(Integer id) {
        CategoriaStand cat = categoriaStandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de stand no encontrada"));
        return toResponse(cat);
    }

    @Override
    public CategoriaStandResponse crear(CategoriaStandRequest request) {
        CategoriaStand entidad = new CategoriaStand();
        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());
        entidad.setColorHex(request.getColorHex());
        entidad.setIconoUrl(request.getIconoUrl());
        entidad.setEstado(request.getEstado() != null ? request.getEstado() : Boolean.TRUE);
        entidad.setEstadoRegistro(1);

        CategoriaStand guardado = categoriaStandRepository.save(entidad);
        return toResponse(guardado);
    }

    @Override
    public CategoriaStandResponse actualizar(Integer id, CategoriaStandRequest request) {
        CategoriaStand entidad = categoriaStandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de stand no encontrada"));

        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());
        entidad.setColorHex(request.getColorHex());
        entidad.setIconoUrl(request.getIconoUrl());
        if (request.getEstado() != null) {
            entidad.setEstado(request.getEstado());
        }

        CategoriaStand guardado = categoriaStandRepository.save(entidad);
        return toResponse(guardado);
    }

    @Override
    public void eliminarLogico(Integer id) {
        CategoriaStand entidad = categoriaStandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de stand no encontrada"));
        entidad.setEstadoRegistro(0);
        entidad.setEstado(false);
        categoriaStandRepository.save(entidad);
    }

    private CategoriaStandResponse toResponse(CategoriaStand cat) {
        CategoriaStandResponse dto = new CategoriaStandResponse();
        dto.setId(cat.getId());
        dto.setNombre(cat.getNombre());
        dto.setDescripcion(cat.getDescripcion());
        dto.setColorHex(cat.getColorHex());
        dto.setIconoUrl(cat.getIconoUrl());
        dto.setEstado(cat.getEstado());
        return dto;
    }
}
