package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Integer> {

    List<CategoriaProducto> findByEstadoRegistro(Integer estadoRegistro);

    List<CategoriaProducto> findByEstadoRegistroAndEstado(Integer estadoRegistro, Boolean estado);

}