package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Producto;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // --------- SOCIO ---------
    @Query("""
            SELECT p FROM Producto p
            JOIN p.stand s
            JOIN s.propietario u
            WHERE s.id = :idStand
              AND u.email = :emailPropietario
              AND p.estadoRegistro = 1
            """)
    List<Producto> findByStandAndPropietario(
            @Param("idStand") Integer idStand,
            @Param("emailPropietario") String emailPropietario
    );
    @Query("""
        SELECT p FROM Producto p
        JOIN p.stand s
        WHERE p.estadoRegistro = 1
          AND p.visibleDirectorio = true
          AND s.id = :idStand
        """)
    List<Producto> buscarPublicoPorStand(
            @Param("idStand") Integer idStand
    );
    // --------- PÚBLICO (si lo usas) ---------
    @Query("""
            SELECT p FROM Producto p
            JOIN p.stand s
            JOIN p.categoriaProducto cp
            WHERE p.estadoRegistro = 1
              AND p.visibleDirectorio = true
              AND (:nombre IS NULL OR LOWER(p.nombre) LIKE :nombre)
              AND (:idCategoriaProducto IS NULL OR cp.id = :idCategoriaProducto)
              AND (:bloque IS NULL OR s.bloque = :bloque)
            """)
    Page<Producto> buscarPublico(
            @Param("nombre") String nombre,
            @Param("idCategoriaProducto") Integer idCategoriaProducto,
            @Param("bloque") String bloque,
            Pageable pageable
    );

    // --------- AUDITORÍA ADMIN / SUPERVISOR ---------
    @Query("""
            SELECT p FROM Producto p
            JOIN p.stand s
            JOIN p.categoriaProducto cp
            WHERE (:nombre IS NULL OR LOWER(p.nombre) LIKE :nombre)
              AND (:idCategoriaProducto IS NULL OR cp.id = :idCategoriaProducto)
              AND (:bloque IS NULL OR s.bloque = :bloque)
              AND (:visible IS NULL OR p.visibleDirectorio = :visible)
              AND (:estadoRegistro IS NULL OR p.estadoRegistro = :estadoRegistro)
            """)
    List<Producto> buscarParaAuditoria(
            @Param("nombre") String nombre,
            @Param("idCategoriaProducto") Integer idCategoriaProducto,
            @Param("bloque") String bloque,
            @Param("visible") Boolean visible,
            @Param("estadoRegistro") Integer estadoRegistro
    );
}