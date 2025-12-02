package com.sise.GestionMercadoMayorista.mapper;

import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioResponseDto;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDto toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setIdUsuario(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setNombres(usuario.getNombres());
        dto.setApellidos(usuario.getApellidos());
        dto.setDni(usuario.getDni());

        if (usuario.getRol() != null) {
            dto.setRol(usuario.getRol().getNombreRol()); // ADMIN, SOCIO, etc.
        }

        dto.setEstado(usuario.getEstado());

        // Asumiendo que en tu entidad son LocalDateTime
        dto.setCreatedAt(usuario.getCreatedAt());
        dto.setUpdatedAt(usuario.getUpdatedAt());

        return dto;
    }
}
