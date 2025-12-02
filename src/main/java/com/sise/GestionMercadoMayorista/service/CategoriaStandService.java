package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.categoriaStand.CategoriaStandRequest;
import com.sise.GestionMercadoMayorista.dto.categoriaStand.CategoriaStandResponse;

import java.util.List;

public interface CategoriaStandService {

    List<CategoriaStandResponse> listarTodas();

    CategoriaStandResponse obtenerPorId(Integer id);

    CategoriaStandResponse crear(CategoriaStandRequest request);

    CategoriaStandResponse actualizar(Integer id, CategoriaStandRequest request);

    void eliminarLogico(Integer id);
}
