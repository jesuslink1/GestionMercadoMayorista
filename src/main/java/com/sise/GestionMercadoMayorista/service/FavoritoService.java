package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.favorito.FavoritoResponseDto;

import java.util.List;

public interface FavoritoService {

    void agregarAFavoritos(Integer idStand);

    void quitarDeFavoritos(Integer idStand);

    List<FavoritoResponseDto> listarMisFavoritos();
}
