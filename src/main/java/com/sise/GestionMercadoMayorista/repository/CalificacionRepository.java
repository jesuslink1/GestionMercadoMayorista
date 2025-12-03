package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Calificacion;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {

    // promedio y cantidad por stand (solo activas)
    @Query("""
       SELECT COALESCE(AVG(c.puntuacion), 0),
              COUNT(c.id)
       FROM Calificacion c
       WHERE c.stand.id = :idStand
         AND c.estadoRegistro = 1
       """)
    Object obtenerPromedioYConteoPorStand(Integer idStand);

    // comentarios recientes por stand
    Page<Calificacion> findByStandIdAndEstadoRegistroOrderByFechaDesc(
            Integer idStand,
            Integer estadoRegistro,
            Pageable pageable
    );
}
