package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.stand.BloqueResumenDto;
import com.sise.GestionMercadoMayorista.dto.stand.StandMapaPublicDto;
import com.sise.GestionMercadoMayorista.dto.stand.StandResponseDto;
import com.sise.GestionMercadoMayorista.service.StandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/stands")
public class PublicStandController {

    private final StandService standService;

    public PublicStandController(StandService standService) {
        this.standService = standService;
    }
    
    @GetMapping("/mapa")
    public List<StandMapaPublicDto> listarParaMapa(
            @RequestParam(required = false) String bloque
    ) {

        List<StandResponseDto> base;

        if (bloque == null || bloque.isBlank()) {
            base = standService.listarTodosActivos();
        } else {
            // usamos tu método de filtros; sólo filtramos por bloque
            base = standService.listarConFiltros(
                    bloque.toUpperCase(Locale.ROOT),
                    null,
                    null
            );
        }

        return base.stream()
                .map(this::mapToMapaDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/mapa/bloques")
    public List<BloqueResumenDto> listarBloquesParaMapa() {

        // Tomamos todos los stands activos
        List<StandResponseDto> base = standService.listarTodosActivos();

        // Agrupamos por bloque y contamos cuántos stands tiene cada uno
        Map<String, Long> conteoPorBloque = base.stream()
                .filter(s -> s.getBloque() != null && !s.getBloque().isBlank())
                .collect(Collectors.groupingBy(
                        s -> s.getBloque().toUpperCase(Locale.ROOT),
                        Collectors.counting()
                ));

        // Lo convertimos a lista de BloqueResumenDto y lo ordenamos alfabéticamente por bloque
        return conteoPorBloque.entrySet().stream()
                .map(e -> new BloqueResumenDto(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(BloqueResumenDto::getBloque))
                .collect(Collectors.toList());
    }

    // Conversión desde tu StandResponseDto al DTO ligero del mapa
    private StandMapaPublicDto mapToMapaDto(StandResponseDto s) {
        StandMapaPublicDto dto = new StandMapaPublicDto();
        dto.setId(s.getId());
        dto.setBloque(s.getBloque());
        dto.setNumeroStand(s.getNumeroStand());

        // Si no hay nombre comercial, mostramos algo amigable
        String nombreComercial = s.getNombreComercial();
        if (nombreComercial == null || nombreComercial.isBlank()) {
            nombreComercial = "Disponible " + s.getNumeroStand();
        }
        dto.setNombreComercial(nombreComercial);

        String rubro = s.getNombreCategoriaStand();
        dto.setRubro((rubro == null || rubro.isBlank()) ? "---" : rubro);

        // Regla simple de ocupación:
        // si tiene propietario => OCUPADO, si no => DISPONIBLE
        dto.setEstado(s.getIdPropietario() != null ? "OCUPADO" : "DISPONIBLE");

        return dto;
    }
}