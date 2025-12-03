package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.calificacion.*;
import com.sise.GestionMercadoMayorista.entity.Calificacion;
import com.sise.GestionMercadoMayorista.entity.Stand;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.CalificacionRepository;
import com.sise.GestionMercadoMayorista.repository.StandRepository;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.CalificacionService;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final StandRepository standRepository;
    private final UsuarioRepository usuarioRepository;

    public CalificacionServiceImpl(CalificacionRepository calificacionRepository,
                                   StandRepository standRepository,
                                   UsuarioRepository usuarioRepository) {
        this.calificacionRepository = calificacionRepository;
        this.standRepository = standRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getUsuarioActualOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || !auth.isAuthenticated()) {
            return null;
        }
        if ("anonymousUser".equalsIgnoreCase(String.valueOf(auth.getPrincipal()))) {
            return null;
        }
        return usuarioRepository.findByEmail(auth.getName()).orElse(null);
    }

    @Override
    public CalificacionResponseDto registrarCalificacion(CalificacionCrearRequestDto dto) {
        if (dto.getIdStand() == null) {
            throw new IllegalArgumentException("Debe indicar el stand.");
        }
        if (dto.getPuntuacion() == null ||
                dto.getPuntuacion() < 1 || dto.getPuntuacion() > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }

        Stand stand = standRepository.findById(dto.getIdStand())
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado."));

        Calificacion c = new Calificacion();
        c.setStand(stand);
        c.setPuntuacion(dto.getPuntuacion());
        c.setComentario(dto.getComentario());
        c.setFecha(LocalDateTime.now());
        c.setEstadoRegistro(1);

        Usuario cliente = getUsuarioActualOrNull();
        if (cliente != null) {
            c.setCliente(cliente);
            c.setOrigen("REGISTRADO");
        } else {
            c.setNombreAnonimo(dto.getNombreAnonimo());
            c.setTelefonoAnonimo(dto.getTelefonoAnonimo());
            c.setEmailAnonimo(dto.getEmailAnonimo());
            c.setOrigen("ANONIMO");
        }

        Calificacion guardada = calificacionRepository.save(c);
        return mapearAResponse(guardada);
    }

    @Override
    public PromedioCalificacionDto obtenerPromedioPorStand(Integer idStand) {
        Stand stand = standRepository.findById(idStand)
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado."));

        Object result = calificacionRepository.obtenerPromedioYConteoPorStand(idStand);

        Object[] row = (Object[]) result;

        double promedio = row[0] != null ? ((Number) row[0]).doubleValue() : 0.0;
        long total = row[1] != null ? ((Number) row[1]).longValue() : 0L;

        PromedioCalificacionDto dto = new PromedioCalificacionDto();
        dto.setIdStand(stand.getId());
        dto.setNombreStand(stand.getNombreComercial());
        dto.setPromedio(promedio);
        dto.setTotalCalificaciones(total);

        return dto;
    }

    @Override
    public Page<CalificacionResponseDto> listarComentariosPorStand(Integer idStand, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Calificacion> pageCal = calificacionRepository
                .findByStandIdAndEstadoRegistroOrderByFechaDesc(idStand, 1, pageable);

        return pageCal.map(this::mapearAResponse);
    }

    private CalificacionResponseDto mapearAResponse(Calificacion c) {
        CalificacionResponseDto dto = new CalificacionResponseDto();
        dto.setIdCalificacion(c.getId());

        if (c.getStand() != null) {
            dto.setIdStand(c.getStand().getId());
            dto.setNombreStand(c.getStand().getNombreComercial());
        }

        dto.setPuntuacion(c.getPuntuacion());
        dto.setComentario(c.getComentario());
        if (c.getFecha() != null) {
            dto.setFecha(c.getFecha().toString());
        }

        if (c.getCliente() != null) {
            dto.setIdCliente(c.getCliente().getId());
            dto.setNombreCliente(
                    c.getCliente().getNombres() + " " + c.getCliente().getApellidos()
            );
        }

        dto.setNombreAnonimo(c.getNombreAnonimo());
        dto.setOrigen(c.getOrigen());

        return dto;
    }
}
