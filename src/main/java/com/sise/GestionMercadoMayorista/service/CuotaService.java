package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.cuota.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CuotaService {

    CuotaResponseDto generarCuotaParaStand(Integer idStand, CuotaRequestDto dto);

    void generarCuotasMasivo(CuotaMasivaRequestDto dto);

    CuotaResponseDto registrarPago(Integer idCuota, PagoCuotaRequestDto dto, String emailUsuario);

    Page<CuotaResponseDto> listarCuotasPorStandAdmin(Integer idStand, int page, int size);

    Page<CuotaResponseDto> listarMisCuotas(String estado, String periodo, int page, int size);

    List<CuotaResponseDto> listarMorosos(String periodo, String bloque, Integer idCategoriaStand);

    IndicadoresCuotasDto obtenerIndicadoresSemaforo(String periodo);
}
