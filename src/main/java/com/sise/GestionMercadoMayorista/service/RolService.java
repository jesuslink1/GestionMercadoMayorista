package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.auth.RolDto;

import java.util.List;

public interface RolService {

    List<RolDto> listarTodos();

    RolDto crearRol(RolDto rolDto);

    RolDto actualizarRol(Integer idRol, RolDto rolDto);

    void eliminarLogico(Integer idRol);
}
