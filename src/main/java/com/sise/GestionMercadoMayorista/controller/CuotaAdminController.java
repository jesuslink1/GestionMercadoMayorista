package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.cuota.*;
import com.sise.GestionMercadoMayorista.service.CuotaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/cuotas")
@SecurityRequirement(name = "bearerAuth")
public class CuotaAdminController {

    private final CuotaService cuotaService;

    public CuotaAdminController(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    @PostMapping("/stand/{idStand}")
    public ResponseEntity<CuotaResponseDto> generarCuotaParaStand(
            @PathVariable Integer idStand,
            @RequestBody CuotaRequestDto dto
    ) {
        CuotaResponseDto resp = cuotaService.generarCuotaParaStand(idStand, dto);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/masivo")
    public ResponseEntity<Void> generarCuotasMasivo(@RequestBody CuotaMasivaRequestDto dto) {
        cuotaService.generarCuotasMasivo(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idCuota}/pagos")
    public ResponseEntity<CuotaResponseDto> registrarPago(
            @PathVariable Integer idCuota,
            @RequestBody PagoCuotaRequestDto dto,
            Principal principal
    ) {
        CuotaResponseDto resp =
                cuotaService.registrarPago(idCuota, dto, principal.getName());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/stand/{idStand}")
    public ResponseEntity<Page<CuotaResponseDto>> listarCuotasStand(
            @PathVariable Integer idStand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<CuotaResponseDto> resultado =
                cuotaService.listarCuotasPorStandAdmin(idStand, page, size);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/morosos")
    public ResponseEntity<List<CuotaResponseDto>> listarMorosos(
            @RequestParam(required = false) String periodo,
            @RequestParam(required = false) String bloque,
            @RequestParam(required = false) Integer idCategoriaStand
    ) {
        List<CuotaResponseDto> lista =
                cuotaService.listarMorosos(periodo, bloque, idCategoriaStand);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/indicadores")
    public ResponseEntity<IndicadoresCuotasDto> indicadores(
            @RequestParam String periodo
    ) {
        IndicadoresCuotasDto dto = cuotaService.obtenerIndicadoresSemaforo(periodo);
        return ResponseEntity.ok(dto);
    }
}
