package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.CuotaPago;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuotaPagoRepository extends JpaRepository<CuotaPago, Integer> {

    // 1) Historial por stand (ADMIN)
    Page<CuotaPago> findByStandIdAndEstadoRegistroOrderByPeriodoDesc(
            Integer idStand,
            Integer estadoRegistro,
            Pageable pageable
    );

    // 2) Buscar cuota específica por stand + periodo (para generar / validar duplicado)
    CuotaPago findByStandIdAndPeriodoAndEstadoRegistro(
            Integer idStand,
            String periodo,
            Integer estadoRegistro
    );

    // 3) Cuotas de un SOCIO (según email propietario)
    @Query("""
           SELECT c FROM CuotaPago c
           JOIN c.stand s
           JOIN s.propietario u
           WHERE u.email = :email
             AND c.estadoRegistro = 1
             AND (:estado IS NULL OR c.estado = :estado)
             AND (:periodo IS NULL OR c.periodo = :periodo)
           ORDER BY c.periodo DESC
           """)
    Page<CuotaPago> buscarCuotasPorSocio(
            @Param("email") String email,
            @Param("estado") String estado,
            @Param("periodo") String periodo,
            Pageable pageable
    );

    // 4) Morosos (PENDIENTE / PARCIAL / VENCIDO)
    @Query("""
           SELECT c FROM CuotaPago c
           JOIN c.stand s
           LEFT JOIN s.categoriaStand cs
           WHERE c.estadoRegistro = 1
             AND c.estado IN ('PENDIENTE', 'PARCIAL', 'VENCIDO')
             AND (:periodo IS NULL OR c.periodo = :periodo)
             AND (:bloque IS NULL OR s.bloque = :bloque)
             AND (:idCategoriaStand IS NULL OR cs.id = :idCategoriaStand)
           """)
    List<CuotaPago> buscarMorosos(
            @Param("periodo") String periodo,
            @Param("bloque") String bloque,
            @Param("idCategoriaStand") Integer idCategoriaStand
    );

    // 5) Todas las cuotas de un periodo (para indicadores)
    @Query("""
           SELECT c FROM CuotaPago c
           JOIN c.stand s
           LEFT JOIN s.categoriaStand cs
           WHERE c.estadoRegistro = 1
             AND c.periodo = :periodo
           """)
    List<CuotaPago> findByPeriodo(@Param("periodo") String periodo);
}