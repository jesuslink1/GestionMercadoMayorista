package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    List<Incidencia> findByStandId(Integer idStand);

    List<Incidencia> findByEstado(String estado);
}