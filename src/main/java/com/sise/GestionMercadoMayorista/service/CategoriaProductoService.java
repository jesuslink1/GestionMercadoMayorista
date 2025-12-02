package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.categoriaProducto.CategoriaProductoRequest;
import com.sise.GestionMercadoMayorista.dto.categoriaProducto.CategoriaProductoResponse;

import java.util.List;

public interface CategoriaProductoService {

    List<CategoriaProductoResponse> listarTodas();

    CategoriaProductoResponse obtenerPorId(Integer id);

    CategoriaProductoResponse crear(CategoriaProductoRequest request);

    CategoriaProductoResponse actualizar(Integer id, CategoriaProductoRequest request);

    void eliminarLogico(Integer id);
}
