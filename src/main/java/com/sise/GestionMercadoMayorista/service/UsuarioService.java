package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.stand.CambiarEstadoUsuarioRequest;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioRequestDto;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioResponseDto;
import com.sise.GestionMercadoMayorista.dto.usuario.UsuarioUpdateRequest;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDto> listarUsuarios(String rolNombre, String estado);

    UsuarioResponseDto obtenerPorId(Integer idUsuario);

    UsuarioResponseDto crearUsuario(UsuarioRequestDto request);

    UsuarioResponseDto actualizarUsuario(Integer id, UsuarioUpdateRequest request);

    void cambiarEstado(Integer idUsuario, CambiarEstadoUsuarioRequest request);

    void eliminarLogico(Integer idUsuario);
}