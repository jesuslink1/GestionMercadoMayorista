package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Incidencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    // 1) Mis incidencias (SOCIO)
    @Query("""
           SELECT i
           FROM Incidencia i
           JOIN i.reportante u
           WHERE u.email = :email
             AND i.estadoRegistro = 1
             AND (:estado IS NULL OR i.estado = :estado)
           ORDER BY i.fechaReporte DESC
           """)
    Page<Incidencia> buscarMisIncidencias(
            @Param("email") String email,
            @Param("estado") String estado,
            Pageable pageable
    );

    // 2) Listado general (ADMIN / SUPERVISOR)
    @Query("""
           SELECT i
           FROM Incidencia i
           WHERE i.estadoRegistro = 1
             AND (:estado IS NULL OR i.estado = :estado)
             AND (:tipo IS NULL OR i.tipo = :tipo)
             AND (:prioridad IS NULL OR i.prioridad = :prioridad)
           ORDER BY i.fechaReporte DESC
           """)
    Page<Incidencia> buscarIncidenciasAdmin(
            @Param("estado") String estado,
            @Param("tipo") String tipo,
            @Param("prioridad") String prioridad,
            Pageable pageable
    );

    // 3) Para reportes mensuales (simple: traemos todas y agrupamos en el servicio)
    List<Incidencia> findByEstadoRegistro(Integer estadoRegistro);

    // 4) Para reporte por responsable (todas las incidencias por estado)
    @Query("""
       SELECT i.responsable.id,
              CONCAT(i.responsable.nombres, ' ', i.responsable.apellidos),
              SUM(CASE WHEN i.estado = 'ABIERTA' THEN 1 ELSE 0 END),
              SUM(CASE WHEN i.estado = 'EN_PROCESO' THEN 1 ELSE 0 END),
              SUM(CASE WHEN i.estado = 'RESUELTA' THEN 1 ELSE 0 END),
              SUM(CASE WHEN i.estado = 'CERRADA' THEN 1 ELSE 0 END)
       FROM Incidencia i
       WHERE i.estadoRegistro = 1
         AND i.responsable IS NOT NULL
       GROUP BY i.responsable.id,
                i.responsable.nombres,
                i.responsable.apellidos
       """)
    List<Object[]> resumenPorResponsable();
}