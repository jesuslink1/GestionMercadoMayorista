package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.cliente.ClienteMeResponseDto;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.ClienteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final UsuarioRepository usuarioRepository;

    public ClienteServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getUsuarioActualObligatorio() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || !auth.isAuthenticated()) {
            throw new IllegalArgumentException("No autenticado.");
        }
        if ("anonymousUser".equalsIgnoreCase(String.valueOf(auth.getPrincipal()))) {
            throw new IllegalArgumentException("No autenticado.");
        }
        return usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
    }

    @Override
    public ClienteMeResponseDto obtenerMe() {
        Usuario u = getUsuarioActualObligatorio();

        ClienteMeResponseDto dto = new ClienteMeResponseDto();
        dto.setIdCliente(u.getId());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        dto.setDni(u.getDni());
        dto.setNombres(u.getNombres());
        dto.setApellidos(u.getApellidos());
        dto.setRuc(u.getRuc());
        dto.setRazonSocial(u.getRazonSocial());
        dto.setRol(u.getRol().getNombreRol());

        return dto;
    }
}
