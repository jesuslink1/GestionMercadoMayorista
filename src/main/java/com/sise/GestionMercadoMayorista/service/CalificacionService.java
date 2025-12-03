package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.calificacion.*;
import org.springframework.data.domain.Page;

public interface CalificacionService {

    CalificacionResponseDto registrarCalificacion(CalificacionCrearRequestDto dto);

    PromedioCalificacionDto obtenerPromedioPorStand(Integer idStand);

    Page<CalificacionResponseDto> listarComentariosPorStand(Integer idStand, int page, int size);
}