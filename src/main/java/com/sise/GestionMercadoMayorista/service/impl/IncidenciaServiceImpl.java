package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.incidencia.*;
import com.sise.GestionMercadoMayorista.entity.Incidencia;
import com.sise.GestionMercadoMayorista.entity.Stand;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.IncidenciaRepository;
import com.sise.GestionMercadoMayorista.repository.StandRepository;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.IncidenciaService;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final StandRepository standRepository;
    private final UsuarioRepository usuarioRepository;

    public IncidenciaServiceImpl(IncidenciaRepository incidenciaRepository,
                                 StandRepository standRepository,
                                 UsuarioRepository usuarioRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.standRepository = standRepository;
        this.usuarioRepository = usuarioRepository;
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

    private Usuario getUsuarioActual() {
        String email = getEmailActual();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
    }

    // ================= SOCIO =================

    @Override
    public IncidenciaResponseDto crearIncidencia(IncidenciaCrearRequestDto dto) {
        Usuario reportante = getUsuarioActual();

        Stand stand = standRepository.findById(dto.getIdStand())
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado."));

        Incidencia i = new Incidencia();
        i.setStand(stand);
        i.setReportante(reportante);
        i.setTitulo(dto.getTitulo());
        i.setDescripcion(dto.getDescripcion());
        i.setTipo(dto.getTipo());
        i.setPrioridad(dto.getPrioridad());
        i.setFotoUrl(dto.getFotoUrl());
        i.setEstado("ABIERTA");
        i.setFechaReporte(LocalDateTime.now());
        i.setEstadoRegistro(1);

        Incidencia guardada = incidenciaRepository.save(i);
        return mapearAResponse(guardada);
    }

    @Override
    public Page<IncidenciaResponseDto> listarMisIncidencias(String estado, int page, int size) {
        String email = getEmailActual();
        Pageable pageable = PageRequest.of(page, size);

        String filtroEstado = (estado != null && !estado.isBlank()) ? estado : null;

        Page<Incidencia> pageInc = incidenciaRepository
                .buscarMisIncidencias(email, filtroEstado, pageable);

        return pageInc.map(this::mapearAResponse);
    }

    // ================= ADMIN / SUPERVISOR =================

    @Override
    public Page<IncidenciaResponseDto> listarIncidencias(String estado, String tipo, String prioridad,
                                                         int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        String e = (estado != null && !estado.isBlank()) ? estado : null;
        String t = (tipo != null && !tipo.isBlank()) ? tipo : null;
        String p = (prioridad != null && !prioridad.isBlank()) ? prioridad : null;

        Page<Incidencia> pageInc = incidenciaRepository
                .buscarIncidenciasAdmin(e, t, p, pageable);

        return pageInc.map(this::mapearAResponse);
    }

    @Override
    public IncidenciaResponseDto asignarResponsable(Integer idIncidencia, Integer idResponsable) {
        Incidencia i = incidenciaRepository.findById(idIncidencia)
                .orElseThrow(() -> new IllegalArgumentException("Incidencia no encontrada."));

        Usuario responsable = usuarioRepository.findById(idResponsable)
                .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado."));

        i.setResponsable(responsable);
        Incidencia guardada = incidenciaRepository.save(i);
        return mapearAResponse(guardada);
    }

    @Override
    public IncidenciaResponseDto cambiarEstado(Integer idIncidencia, String nuevoEstado) {
        Incidencia i = incidenciaRepository.findById(idIncidencia)
                .orElseThrow(() -> new IllegalArgumentException("Incidencia no encontrada."));

        validarTransicionEstado(i.getEstado(), nuevoEstado);

        i.setEstado(nuevoEstado);

        // Si pasa a RESUELTA o CERRADA, registramos fecha_cierre
        if ("RESUELTA".equalsIgnoreCase(nuevoEstado) || "CERRADA".equalsIgnoreCase(nuevoEstado)) {
            i.setFechaCierre(LocalDateTime.now());
        }

        Incidencia guardada = incidenciaRepository.save(i);
        return mapearAResponse(guardada);
    }

    private void validarTransicionEstado(String actual, String nuevo) {
        if (actual == null) actual = "ABIERTA";

        // reglas simples:
        // ABIERTA -> EN_PROCESO / CERRADA (si la cierran sin trabajar)
        // EN_PROCESO -> RESUELTA / CERRADA
        // RESUELTA -> CERRADA
        // CERRADA -> no se cambia
        if ("CERRADA".equalsIgnoreCase(actual)) {
            throw new IllegalArgumentException("La incidencia ya está cerrada.");
        }

        Set<String> permitidosDesdeAbierta = Set.of("EN_PROCESO", "CERRADA");
        Set<String> permitidosDesdeEnProceso = Set.of("RESUELTA", "CERRADA");
        Set<String> permitidosDesdeResuelta = Set.of("CERRADA");

        String act = actual.toUpperCase();
        String nue = nuevo.toUpperCase();

        boolean ok;
        switch (act) {
            case "ABIERTA" -> ok = permitidosDesdeAbierta.contains(nue);
            case "EN_PROCESO" -> ok = permitidosDesdeEnProceso.contains(nue);
            case "RESUELTA" -> ok = permitidosDesdeResuelta.contains(nue);
            default -> ok = false;
        }

        if (!ok) {
            throw new IllegalArgumentException(
                    "Transición de estado no válida: " + actual + " -> " + nuevo
            );
        }
    }

    // ================= REPORTES =================

    @Override
    public List<IncidenciaResumenMensualDto> resumenMensual() {
        List<Incidencia> lista = incidenciaRepository.findByEstadoRegistro(1);

        // Agrupamos por YearMonth(fecha_reporte)
        Map<YearMonth, List<Incidencia>> porMes = lista.stream()
                .filter(i -> i.getFechaReporte() != null)
                .collect(Collectors.groupingBy(i ->
                        YearMonth.from(i.getFechaReporte())
                ));

        List<IncidenciaResumenMensualDto> resultado = new ArrayList<>();

        for (Map.Entry<YearMonth, List<Incidencia>> entry : porMes.entrySet()) {
            YearMonth ym = entry.getKey();
            List<Incidencia> incMes = entry.getValue();

            IncidenciaResumenMensualDto dto = new IncidenciaResumenMensualDto();
            dto.setAnio(ym.getYear());
            dto.setMes(ym.getMonthValue());

            dto.setTotalAbiertas(incMes.stream().filter(i -> "ABIERTA".equals(i.getEstado())).count());
            dto.setTotalEnProceso(incMes.stream().filter(i -> "EN_PROCESO".equals(i.getEstado())).count());
            dto.setTotalResueltas(incMes.stream().filter(i -> "RESUELTA".equals(i.getEstado())).count());
            dto.setTotalCerradas(incMes.stream().filter(i -> "CERRADA".equals(i.getEstado())).count());

            resultado.add(dto);
        }

        // Orden por año/mes descendente (opcional)
        resultado.sort(Comparator.comparing(IncidenciaResumenMensualDto::getAnio)
                .thenComparing(IncidenciaResumenMensualDto::getMes));

        return resultado;
    }

    @Override
    public List<IncidenciaPorResponsableDto> resumenPorResponsable() {
        List<Object[]> rows = incidenciaRepository.resumenPorResponsable();
        List<IncidenciaPorResponsableDto> lista = new ArrayList<>();

        for (Object[] r : rows) {
            IncidenciaPorResponsableDto dto = new IncidenciaPorResponsableDto();
            dto.setIdResponsable((Integer) r[0]);
            dto.setNombreResponsable((String) r[1]);

            // OJO: depende del provider JPA esto puede ser Long, BigInteger, etc.
            dto.setTotalAbiertas(((Number) r[2]).longValue());
            dto.setTotalEnProceso(((Number) r[3]).longValue());
            dto.setTotalResueltas(((Number) r[4]).longValue());
            dto.setTotalCerradas(((Number) r[5]).longValue());

            lista.add(dto);
        }

        return lista;
    }

    // ================= MAPPER =================

    private IncidenciaResponseDto mapearAResponse(Incidencia i) {
        IncidenciaResponseDto dto = new IncidenciaResponseDto();
        dto.setIdIncidencia(i.getId());

        if (i.getStand() != null) {
            dto.setIdStand(i.getStand().getId());
            dto.setNombreStand(i.getStand().getNombreComercial());
            dto.setBloque(i.getStand().getBloque());
            dto.setNumeroStand(i.getStand().getNumeroStand());
        }

        if (i.getReportante() != null) {
            dto.setIdReportante(i.getReportante().getId());
            dto.setNombreReportante(
                    i.getReportante().getNombres() + " " + i.getReportante().getApellidos()
            );
        }

        if (i.getResponsable() != null) {
            dto.setIdResponsable(i.getResponsable().getId());
            dto.setNombreResponsable(
                    i.getResponsable().getNombres() + " " + i.getResponsable().getApellidos()
            );
        }

        dto.setTitulo(i.getTitulo());
        dto.setDescripcion(i.getDescripcion());
        dto.setTipo(i.getTipo());
        dto.setPrioridad(i.getPrioridad());
        dto.setEstado(i.getEstado());
        dto.setFotoUrl(i.getFotoUrl());

        if (i.getFechaReporte() != null) {
            dto.setFechaReporte(i.getFechaReporte().toString());
        }
        if (i.getFechaCierre() != null) {
            dto.setFechaCierre(i.getFechaCierre().toString());
        }

        return dto;
    }
}
