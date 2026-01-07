package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.stand.BloqueResumenDto;
import com.sise.GestionMercadoMayorista.dto.stand.StandMapaPublicDto;
import com.sise.GestionMercadoMayorista.dto.stand.StandResponseDto;
import com.sise.GestionMercadoMayorista.service.StandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        List<StandResponseDto> base = standService.listarTodosActivos();

        Map<String, Long> conteoPorBloque = base.stream()
                .filter(s -> s.getBloque() != null && !s.getBloque().isBlank())
                .collect(Collectors.groupingBy(
                        s -> s.getBloque().toUpperCase(Locale.ROOT),
                        Collectors.counting()
                ));

        return conteoPorBloque.entrySet().stream()
                .map(e -> new BloqueResumenDto(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(BloqueResumenDto::getBloque))
                .collect(Collectors.toList());
    }

    // stand por ID
    @GetMapping("/{id}")
    public ResponseEntity<StandResponseDto> obtenerStandPublico(@PathVariable Integer id) {
        // Usa tu servicio existente
        StandResponseDto stand = standService.obtenerPorId(id);
        return ResponseEntity.ok(stand);
    }

    // Conversión para el mapa
    private StandMapaPublicDto mapToMapaDto(StandResponseDto s) {
        StandMapaPublicDto dto = new StandMapaPublicDto();
        dto.setId(s.getId());
        dto.setBloque(s.getBloque());
        dto.setNumeroStand(s.getNumeroStand());

        String nombreComercial = s.getNombreComercial();
        if (nombreComercial == null || nombreComercial.isBlank()) {
            nombreComercial = "Disponible " + s.getNumeroStand();
        }
        dto.setNombreComercial(nombreComercial);

        String rubro = s.getNombreCategoriaStand();
        dto.setRubro((rubro == null || rubro.isBlank()) ? "---" : rubro);

        dto.setEstado(s.getEstado() != null ? s.getEstado() : "DISPONIBLE");

        return dto;
    }
}
