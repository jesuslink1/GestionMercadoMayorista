package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StandRepository extends JpaRepository<Stand, Integer> {

    // Para listar todos los stands activos (estado_registro = 1)
    List<Stand> findByEstadoRegistro(Integer estadoRegistro);

    // Filtros: bloque, categoría, estado
    @Query("SELECT s FROM Stand s " +
            "WHERE (s.estadoRegistro IS NULL OR s.estadoRegistro = 1) " +
            "AND (:bloque IS NULL OR s.bloque = :bloque) " +
            "AND (:idCategoria IS NULL OR s.categoriaStand.id = :idCategoria) " +
            "AND (:estado IS NULL OR s.estado = :estado)")
    List<Stand> buscarConFiltros(@Param("bloque") String bloque,
                                 @Param("idCategoria") Integer idCategoria,
                                 @Param("estado") String estado);

    // Stands de un socio por id de propietario
    List<Stand> findByPropietarioIdAndEstadoRegistro(Integer idPropietario, Integer estadoRegistro);

    // Se usa para asegurar que el SOCIO solo pueda gestionar productos de SUS stands.
    Optional<Stand> findByIdAndPropietarioEmailAndEstadoRegistro(Integer id,
                                                                 String email,
                                                                 Integer estadoRegistro);
}