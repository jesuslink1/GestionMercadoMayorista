package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

    List<Favorito> findByClienteId(Integer idCliente);
}
