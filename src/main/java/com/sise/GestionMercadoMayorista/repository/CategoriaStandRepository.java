package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.CategoriaStand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaStandRepository extends JpaRepository<CategoriaStand, Integer> {
    List<CategoriaStand> findByEstadoRegistro(Integer estadoRegistro);
}