package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.favorito.FavoritoResponseDto;
import com.sise.GestionMercadoMayorista.entity.Favorito;
import com.sise.GestionMercadoMayorista.entity.Stand;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.FavoritoRepository;
import com.sise.GestionMercadoMayorista.repository.StandRepository;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.FavoritoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final StandRepository standRepository;

    public FavoritoServiceImpl(FavoritoRepository favoritoRepository,
                               UsuarioRepository usuarioRepository,
                               StandRepository standRepository) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.standRepository = standRepository;
    }

    private Usuario getUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalArgumentException("No se pudo obtener el usuario autenticado.");
        }
        return usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
    }

    @Override
    public void agregarAFavoritos(Integer idStand) {
        Usuario cliente = getUsuarioActual();

        Stand stand = standRepository.findById(idStand)
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado."));

        Optional<Favorito> opt = favoritoRepository
                .findByClienteIdAndStandId(cliente.getId(), stand.getId());

        if (opt.isPresent()) {
            Favorito f = opt.get();
            if (f.getEstadoRegistro() != null && f.getEstadoRegistro() == 1) {
                // ya está activo
                return;
            }
            f.setEstadoRegistro(1);
            f.setFechaAgregado(LocalDateTime.now());
            favoritoRepository.save(f);
        } else {
            Favorito f = new Favorito();
            f.setCliente(cliente);
            f.setStand(stand);
            f.setFechaAgregado(LocalDateTime.now());
            f.setEstadoRegistro(1);
            favoritoRepository.save(f);
        }
    }

    @Override
    public void quitarDeFavoritos(Integer idStand) {
        Usuario cliente = getUsuarioActual();

        Optional<Favorito> opt = favoritoRepository
                .findByClienteIdAndStandId(cliente.getId(), idStand);

        if (opt.isPresent()) {
            Favorito f = opt.get();
            f.setEstadoRegistro(0);
            favoritoRepository.save(f);
        }
    }

    @Override
    public List<FavoritoResponseDto> listarMisFavoritos() {
        Usuario cliente = getUsuarioActual();

        List<Favorito> lista = favoritoRepository
                .findByClienteIdAndEstadoRegistro(cliente.getId(), 1);

        return lista.stream()
                .map(this::mapearAFavoritoDto)
                .collect(Collectors.toList());
    }

    private FavoritoResponseDto mapearAFavoritoDto(Favorito f) {
        FavoritoResponseDto dto = new FavoritoResponseDto();
        dto.setIdFavorito(f.getId());

        if (f.getStand() != null) {
            dto.setIdStand(f.getStand().getId());
            dto.setNombreStand(f.getStand().getNombreComercial());
            dto.setBloque(f.getStand().getBloque());
            dto.setNumeroStand(f.getStand().getNumeroStand());
            if (f.getStand().getCategoriaStand() != null) {
                dto.setCategoriaStand(f.getStand().getCategoriaStand().getNombre());
            }
        }

        if (f.getFechaAgregado() != null) {
            dto.setFechaAgregado(f.getFechaAgregado().toString());
        }

        return dto;
    }
}
