package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.stand.StandRequestDto;
import com.sise.GestionMercadoMayorista.dto.stand.StandResponseDto;

import java.util.List;

public interface StandService {


    List<StandResponseDto> listarTodosActivos();

    List<StandResponseDto> listarConFiltros(String bloque,
                                            Integer idCategoriaStand,
                                            String estado);

    List<StandResponseDto> listarPorPropietario(Integer idPropietario);

    StandResponseDto obtenerPorId(Integer id);

    StandResponseDto crear(StandRequestDto request);

    StandResponseDto actualizar(Integer id, StandRequestDto request);

    void cambiarEstado(Integer id, String nuevoEstado);

    void eliminarLogico(Integer id);
}