package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.cuota.*;
import com.sise.GestionMercadoMayorista.entity.CuotaPago;
import com.sise.GestionMercadoMayorista.entity.Stand;
import com.sise.GestionMercadoMayorista.repository.CuotaPagoRepository;
import com.sise.GestionMercadoMayorista.repository.StandRepository;
import com.sise.GestionMercadoMayorista.service.CuotaService;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.sise.GestionMercadoMayorista.exception.ReglaNegocioException;
import com.sise.GestionMercadoMayorista.exception.RecursoNoEncontradoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CuotaServiceImpl implements CuotaService {

    private final CuotaPagoRepository cuotaPagoRepository;
    private final StandRepository standRepository;

    public CuotaServiceImpl(CuotaPagoRepository cuotaPagoRepository,
                            StandRepository standRepository) {
        this.cuotaPagoRepository = cuotaPagoRepository;
        this.standRepository = standRepository;
    }

    private Authentication getAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalArgumentException("No se pudo obtener el usuario autenticado.");
        }
        return auth;
    }

    private String getEmailActual() {
        return getAuth().getName();
    }

    // ============================================
    // 1) Generar cuota para un stand
    // ============================================

    @Override
    public CuotaResponseDto generarCuotaParaStand(Integer idStand, CuotaRequestDto dto) {
        Stand stand = standRepository.findById(idStand)
                .orElseThrow(() -> new RecursoNoEncontradoException("Stand no encontrado con id: " + idStand));

        if (stand.getEstadoRegistro() != null && stand.getEstadoRegistro() == 0) {
            throw new ReglaNegocioException("No se pueden generar cuotas para un stand eliminado lógicamente.");
        }

        if (dto.getPeriodo() == null || dto.getPeriodo().isBlank()) {
            throw new ReglaNegocioException("El periodo de la cuota es obligatorio.");
        }

        if (dto.getMontoCuota() == null || dto.getMontoCuota().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaNegocioException("El monto de la cuota debe ser mayor a 0.");
        }

        // Validar duplicado
        CuotaPago existente = cuotaPagoRepository
                .findByStandIdAndPeriodoAndEstadoRegistro(idStand, dto.getPeriodo(), 1);
        if (existente != null) {
            throw new ReglaNegocioException("Ya existe una cuota para este stand y periodo.");
        }

        CuotaPago cuota = new CuotaPago();
        cuota.setStand(stand);
        cuota.setPeriodo(dto.getPeriodo());
        cuota.setMontoCuota(dto.getMontoCuota());
        if (dto.getFechaVencimiento() != null) {
            cuota.setFechaVencimiento(LocalDate.parse(dto.getFechaVencimiento()));
        }
        cuota.setEstado("PENDIENTE");
        cuota.setMontoPagado(BigDecimal.ZERO);
        cuota.setEstadoRegistro(1);

        CuotaPago guardada = cuotaPagoRepository.save(cuota);
        return mapearCuotaAResponse(guardada);
    }

    // ============================================
    // 2) Generar cuotas masivo
    // ============================================

    @Override
    public void generarCuotasMasivo(CuotaMasivaRequestDto dto) {

        if (dto.getMontoCuota() == null || dto.getMontoCuota().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaNegocioException("El monto de la cuota debe ser mayor a 0.");
        }

        List<Stand> stands = obtenerStandsParaCuotaMasiva(
                dto.getBloque(),
                dto.getIdCategoriaStand()
        );

        for (Stand stand : stands) {
            CuotaPago existente = cuotaPagoRepository
                    .findByStandIdAndPeriodoAndEstadoRegistro(
                            stand.getId(), dto.getPeriodo(), 1);
            if (existente != null) {
                continue; // ya tiene cuota ese periodo, lo saltamos
            }

            CuotaPago cuota = new CuotaPago();
            cuota.setStand(stand);
            cuota.setPeriodo(dto.getPeriodo());
            cuota.setMontoCuota(dto.getMontoCuota());
            if (dto.getFechaVencimiento() != null) {
                cuota.setFechaVencimiento(LocalDate.parse(dto.getFechaVencimiento()));
            }
            cuota.setEstado("PENDIENTE");
            cuota.setMontoPagado(BigDecimal.ZERO);
            cuota.setEstadoRegistro(1);

            cuotaPagoRepository.save(cuota);
        }
    }

    private List<Stand> obtenerStandsParaCuotaMasiva(String bloque, Integer idCategoriaStand) {
        // Aquí defines métodos en StandRepository, por ejemplo:
        // findByEstadoRegistro(1)
        // findByBloqueAndEstadoRegistro(...)
        // findByCategoriaStandIdAndEstadoRegistro(...)
        // o un @Query con filtros opcionales.
        //
        // Para no alargar, asume que tienes un método:
        // List<Stand> findByEstadoRegistro(Integer estadoRegistro);
        //
        // y tú luego puedes refinarlo.
        return standRepository.findByEstadoRegistro(1);
    }

    // ============================================
    // 3) Registrar pago
    // ============================================

    @Override
    public CuotaResponseDto registrarPago(Integer idCuota, PagoCuotaRequestDto dto, String emailUsuario) {
        CuotaPago cuota = cuotaPagoRepository.findById(idCuota)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuota no encontrada con id: " + idCuota));

        if (cuota.getEstadoRegistro() != null && cuota.getEstadoRegistro() == 0) {
            throw new ReglaNegocioException("No se pueden registrar pagos sobre una cuota eliminada lógicamente.");
        }

        BigDecimal monto = dto.getMontoPagado();
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaNegocioException("El monto del pago debe ser mayor a 0.");
        }

        // Bloquear si ya está pagada
        if ("PAGADO".equalsIgnoreCase(cuota.getEstado())) {
            throw new ReglaNegocioException("La cuota ya está pagada; no se pueden registrar más pagos.");
        }

        // Validar que no se pague más que el saldo pendiente
        BigDecimal pagadoActual = cuota.getMontoPagado() != null ? cuota.getMontoPagado() : BigDecimal.ZERO;
        BigDecimal saldo = cuota.getMontoCuota().subtract(pagadoActual);
        if (monto.compareTo(saldo) > 0) {
            throw new ReglaNegocioException("El pago excede el saldo pendiente. Saldo actual: " + saldo);
        }

        // Aplicar pago (ya sin sobrepago)
        aplicarPago(cuota, monto);

        LocalDate fechaPago = (dto.getFechaPago() != null)
                ? LocalDate.parse(dto.getFechaPago())
                : LocalDate.now();

        cuota.setFechaPago(fechaPago);
        cuota.setMedioPago(dto.getMedioPago());
        cuota.setReferenciaPago(dto.getReferenciaPago());
        cuota.setObservaciones(dto.getObservaciones());

        CuotaPago actualizada = cuotaPagoRepository.save(cuota);
        return mapearCuotaAResponse(actualizada);
    }

    private void aplicarPago(CuotaPago cuota, BigDecimal montoPago) {
        if (cuota.getMontoPagado() == null) {
            cuota.setMontoPagado(BigDecimal.ZERO);
        }

        BigDecimal nuevoPagado = cuota.getMontoPagado().add(montoPago);
        cuota.setMontoPagado(nuevoPagado);

        if (nuevoPagado.compareTo(cuota.getMontoCuota()) == 0) {
            cuota.setEstado("PAGADO");
        } else if (nuevoPagado.compareTo(BigDecimal.ZERO) > 0) {
            cuota.setEstado("PARCIAL");
        } else {
            cuota.setEstado("PENDIENTE");
        }
    }

    // ============================================
    // 4) Historial por stand (ADMIN)
    // ============================================

    @Override
    public Page<CuotaResponseDto> listarCuotasPorStandAdmin(Integer idStand, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CuotaPago> pageCuotas = cuotaPagoRepository
                .findByStandIdAndEstadoRegistroOrderByPeriodoDesc(idStand, 1, pageable);

        return pageCuotas.map(this::mapearCuotaAResponse);
    }

    // ============================================
    // 5) Mis cuotas (SOCIO)
    // ============================================

    @Override
    public Page<CuotaResponseDto> listarMisCuotas(String estado, String periodo, int page, int size) {
        String email = getEmailActual();
        Pageable pageable = PageRequest.of(page, size);

        Page<CuotaPago> pageCuotas = cuotaPagoRepository
                .buscarCuotasPorSocio(email,
                        (estado != null && !estado.isBlank()) ? estado : null,
                        (periodo != null && !periodo.isBlank()) ? periodo : null,
                        pageable);

        return pageCuotas.map(this::mapearCuotaAResponse);
    }

    // ============================================
    // 6) Morosos
    // ============================================

    @Override
    public List<CuotaResponseDto> listarMorosos(String periodo, String bloque, Integer idCategoriaStand) {
        List<CuotaPago> morosos = cuotaPagoRepository
                .buscarMorosos(
                        (periodo != null && !periodo.isBlank()) ? periodo : null,
                        (bloque != null && !bloque.isBlank()) ? bloque : null,
                        idCategoriaStand
                );

        return morosos.stream()
                .map(this::mapearCuotaAResponse)
                .collect(Collectors.toList());
    }

    // ============================================
    // 7) Indicadores semáforo
    // ============================================

    @Override
    public IndicadoresCuotasDto obtenerIndicadoresSemaforo(String periodo) {
        List<CuotaPago> cuotas = cuotaPagoRepository.findByPeriodo(periodo);

        IndicadoresCuotasDto dto = new IndicadoresCuotasDto();
        dto.setPeriodo(periodo);

        long total = cuotas.size();
        long alDia = cuotas.stream()
                .filter(c -> "PAGADO".equals(c.getEstado()))
                .count();
        long morosos = total - alDia;

        dto.setTotalStandsConCuota(total);
        dto.setStandsAlDia(alDia);
        dto.setStandsMorosos(morosos);

        if (total > 0) {
            dto.setPorcentajeAlDia(alDia * 100.0 / total);
            dto.setPorcentajeMorosos(morosos * 100.0 / total);
        }

        // Morosidad por bloque
        Map<String, List<CuotaPago>> porBloque = cuotas.stream()
                .collect(Collectors.groupingBy(c -> c.getStand().getBloque()));

        List<IndicadoresCuotasDto.MorosidadBloqueDto> listaBloques =
                porBloque.entrySet().stream()
                        .map(entry -> {
                            String bloque = entry.getKey();
                            List<CuotaPago> list = entry.getValue();

                            long totalB = list.size();
                            long alDiaB = list.stream()
                                    .filter(c -> "PAGADO".equals(c.getEstado()))
                                    .count();
                            long morososB = totalB - alDiaB;

                            IndicadoresCuotasDto.MorosidadBloqueDto bDto =
                                    new IndicadoresCuotasDto.MorosidadBloqueDto();
                            bDto.setBloque(bloque);
                            bDto.setStandsAlDia(alDiaB);
                            bDto.setStandsMorosos(morososB);
                            return bDto;
                        })
                        .collect(Collectors.toList());

        dto.setMorosidadPorBloque(listaBloques);

        return dto;
    }

    // ============================================
    // Mapper Entity -> DTO
    // ============================================

    private CuotaResponseDto mapearCuotaAResponse(CuotaPago c) {
        CuotaResponseDto dto = new CuotaResponseDto();
        dto.setIdCuota(c.getId());

        if (c.getStand() != null) {
            dto.setIdStand(c.getStand().getId());
            dto.setNombreStand(c.getStand().getNombreComercial());
            dto.setBloque(c.getStand().getBloque());
            dto.setNumeroStand(c.getStand().getNumeroStand());
        }

        dto.setPeriodo(c.getPeriodo());
        dto.setMontoCuota(c.getMontoCuota());
        dto.setMontoPagado(c.getMontoPagado());

        if (c.getMontoCuota() != null && c.getMontoPagado() != null) {
            dto.setSaldoPendiente(c.getMontoCuota().subtract(c.getMontoPagado()));
        }

        dto.setEstado(c.getEstado());
        if (c.getFechaVencimiento() != null) {
            dto.setFechaVencimiento(c.getFechaVencimiento().toString());
        }
        if (c.getFechaPago() != null) {
            dto.setFechaPago(c.getFechaPago().toString());
        }

        dto.setMedioPago(c.getMedioPago());
        dto.setReferenciaPago(c.getReferenciaPago());

        return dto;
    }
}
