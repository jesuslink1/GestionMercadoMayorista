package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.stand.CambiarEstadoUsuarioRequest;
import com.sise.GestionMercadoMayorista.dto.usuario.RegistroClienteRequest;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioRequestDto;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioResponseDto;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioUpdateRequest;
import com.sise.GestionMercadoMayorista.entity.Rol;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.RolRepository;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sise.GestionMercadoMayorista.mapper.UsuarioMapper;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              RolRepository rolRepository,
                              PasswordEncoder passwordEncoder,
                              UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public List<UsuarioResponseDto> listarUsuarios(String rolNombre, String estado) {
        List<Usuario> usuarios = usuarioRepository.buscarPorRolYEstado(rolNombre, estado);
        return usuarios.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDto obtenerPorId(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));

        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponseDto crearUsuario(UsuarioRequestDto request) {
        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + request.getIdRol()));

        Usuario usuario = new Usuario();
        usuario.setRol(rol);
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDni(request.getDni());
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setRuc(request.getRuc());
        usuario.setRazonSocial(request.getRazonSocial());
        usuario.setEstado("ACTIVO");
        usuario.setEstadoRegistro(1);

        Usuario guardado = usuarioRepository.save(usuario);
        return mapToResponse(guardado);
    }

    @Override
    @Transactional
    public UsuarioResponseDto actualizarUsuario(Integer id, UsuarioUpdateRequest request) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // OJO: NO tocamos email, password, rol, etc.
        if (request.getNombres() != null) {
            usuario.setNombres(request.getNombres());
        }
        if (request.getApellidos() != null) {
            usuario.setApellidos(request.getApellidos());
        }
        if (request.getTelefono() != null) {
            usuario.setTelefono(request.getTelefono());
        }
        if (request.getFotoUrl() != null) {
            usuario.setFotoUrl(request.getFotoUrl());
        }

        usuario.setUpdatedAt(LocalDateTime.now());

        Usuario guardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(guardado);
    }

    @Override
    public void cambiarEstado(Integer idUsuario, CambiarEstadoUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));

        String nuevoEstado = request.getEstado();

        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede ser nulo o vacío");
        }

        // Normalizamos a MAYÚSCULAS: ACTIVO / SUSPENDIDO / BAJA
        usuario.setEstado(nuevoEstado.toUpperCase());

        usuario.setUpdatedAt(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarLogico(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));

        usuario.setEstadoRegistro(0);
        usuario.setEstado("BAJA");
        usuarioRepository.save(usuario);
    }

    private UsuarioResponseDto mapToResponse(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setIdUsuario(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setDni(usuario.getDni());
        dto.setNombres(usuario.getNombres());
        dto.setApellidos(usuario.getApellidos());
        dto.setRol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : null);
        dto.setEstado(usuario.getEstado());
        dto.setEstadoRegistro(usuario.getEstadoRegistro());
        if (usuario.getCreatedAt() != null) {
            dto.setCreatedAt(usuario.getCreatedAt());
        }
        return dto;
    }

    @Override
    public UsuarioResponseDto registrarClientePublico(RegistroClienteRequest request) {

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        // Validar que no exista otro usuario con el mismo email
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese email");
        }

        // Buscar rol CLIENTE
        Rol rolCliente = rolRepository.findByNombreRolIgnoreCase("CLIENTE")
                .orElseThrow(() -> new RuntimeException("No se encontró el rol CLIENTE"));

        Usuario usuario = new Usuario();
        usuario.setRol(rolCliente);
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEstado("ACTIVO");
        usuario.setEstadoRegistro(1);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        Usuario guardado = usuarioRepository.save(usuario);
        return mapToResponse(guardado); // reutilizamos tu mismo mapper
    }
}
