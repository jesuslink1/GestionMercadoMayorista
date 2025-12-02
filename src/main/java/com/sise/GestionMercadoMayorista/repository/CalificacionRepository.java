package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {

    List<Calificacion> findByStandId(Integer idStand);
}
