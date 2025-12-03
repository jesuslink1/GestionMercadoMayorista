package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.incidencia.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IncidenciaService {

    // SOCIO
    IncidenciaResponseDto crearIncidencia(IncidenciaCrearRequestDto dto);

    Page<IncidenciaResponseDto> listarMisIncidencias(
            String estado,
            int page,
            int size
    );

    // ADMIN / SUPERVISOR
    Page<IncidenciaResponseDto> listarIncidencias(
            String estado,
            String tipo,
            String prioridad,
            int page,
            int size
    );

    IncidenciaResponseDto asignarResponsable(Integer idIncidencia, Integer idResponsable);

    IncidenciaResponseDto cambiarEstado(Integer idIncidencia, String nuevoEstado);

    // REPORTES
    List<IncidenciaResumenMensualDto> resumenMensual();

    List<IncidenciaPorResponsableDto> resumenPorResponsable();
}
