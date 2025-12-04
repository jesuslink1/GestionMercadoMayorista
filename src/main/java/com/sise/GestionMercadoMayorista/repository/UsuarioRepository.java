package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByDni(String dni);

    @Query("SELECT u FROM Usuario u " +
            "WHERE (:rolNombre IS NULL OR u.rol.nombreRol = :rolNombre) " +
            "AND (:estado IS NULL OR u.estado = :estado) " +
            "AND u.estadoRegistro = 1")
    List<Usuario> buscarPorRolYEstado(@Param("rolNombre") String rolNombre,
                                      @Param("estado") String estado);
}
