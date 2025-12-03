package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

    Optional<Favorito> findByClienteIdAndStandId(Integer idCliente, Integer idStand);

    List<Favorito> findByClienteIdAndEstadoRegistro(Integer idCliente, Integer estadoRegistro);
}
