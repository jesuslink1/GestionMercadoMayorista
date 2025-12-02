package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.auth.RolDto;
import com.sise.GestionMercadoMayorista.entity.Rol;
import com.sise.GestionMercadoMayorista.repository.RolRepository;
import com.sise.GestionMercadoMayorista.service.RolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<RolDto> listarTodos() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RolDto crearRol(RolDto rolDto) {
        Rol rol = new Rol();
        rol.setNombreRol(rolDto.getNombreRol());
        rol.setDescripcion(rolDto.getDescripcion());
        rol.setEstadoRegistro(1);

        Rol guardado = rolRepository.save(rol);
        return mapToDto(guardado);
    }

    @Override
    public RolDto actualizarRol(Integer idRol, RolDto rolDto) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + idRol));

        rol.setNombreRol(rolDto.getNombreRol());
        rol.setDescripcion(rolDto.getDescripcion());

        Rol actualizado = rolRepository.save(rol);
        return mapToDto(actualizado);
    }

    @Override
    public void eliminarLogico(Integer idRol) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + idRol));

        rol.setEstadoRegistro(0);
        rolRepository.save(rol);
    }

    private RolDto mapToDto(Rol rol) {
        RolDto dto = new RolDto();
        dto.setIdRol(rol.getId());
        dto.setNombreRol(rol.getNombreRol());
        dto.setDescripcion(rol.getDescripcion());
        dto.setEstadoRegistro(rol.getEstadoRegistro());
        return dto;
    }
}