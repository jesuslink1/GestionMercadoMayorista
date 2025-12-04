package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.cuota.CuotaResponseDto;
import com.sise.GestionMercadoMayorista.service.CuotaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/socio/cuotas")
@SecurityRequirement(name = "bearerAuth")
public class CuotaSocioController {

    private final CuotaService cuotaService;

    public CuotaSocioController(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    @GetMapping("/mis-cuotas")
    public ResponseEntity<Page<CuotaResponseDto>> listarMisCuotas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String periodo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<CuotaResponseDto> resultado =
                cuotaService.listarMisCuotas(estado, periodo, page, size);
        return ResponseEntity.ok(resultado);
    }
}
