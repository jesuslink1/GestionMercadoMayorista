package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.credencial.CrearCredencialRequest;
import com.sise.GestionMercadoMayorista.dto.credencial.CredencialResponse;
import com.sise.GestionMercadoMayorista.dto.credencial.ValidacionQrResponse;
import com.sise.GestionMercadoMayorista.entity.CredencialQr;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.CredencialQrRepository;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.CredencialQrService;
import com.sise.GestionMercadoMayorista.util.QrTokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sise.GestionMercadoMayorista.exception.RecursoNoEncontradoException;
import com.sise.GestionMercadoMayorista.exception.ReglaNegocioException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CredencialQrServiceImpl implements CredencialQrService {

    private final CredencialQrRepository credencialQrRepository;
    private final UsuarioRepository usuarioRepository;

    public CredencialQrServiceImpl(CredencialQrRepository credencialQrRepository,
                                   UsuarioRepository usuarioRepository) {
        this.credencialQrRepository = credencialQrRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public CredencialResponse crearCredencial(CrearCredencialRequest request) {

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con id: " + request.getIdUsuario()
                ));

        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            throw new ReglaNegocioException(
                    "Solo se pueden generar credenciales para usuarios ACTIVO"
            );
        }

        // invalidar la última credencial (opcional)
        credencialQrRepository
                .findFirstByIdUsuarioAndEstadoRegistroOrderByFechaEmisionDesc(
                        usuario.getId(), 1
                )
                .ifPresent(c -> {
                    c.setVigente(false);
                    credencialQrRepository.save(c);
                });

        CredencialQr credencial = new CredencialQr();
        credencial.setIdUsuario(usuario.getId());
        credencial.setCodigoQr(QrTokenGenerator.generarCodigoQr());
        credencial.setTipoCredencial(
                request.getTipoCredencial() != null ? request.getTipoCredencial() : "SOCIO"
        );
        credencial.setFechaEmision(LocalDate.now());
        credencial.setFechaVencimiento(
                request.getFechaVencimiento() != null
                        ? request.getFechaVencimiento()
                        : LocalDate.now().plusYears(1)
        );
        credencial.setVigente(true);
        credencial.setEstadoRegistro(1);

        credencial = credencialQrRepository.save(credencial);

        return mapToResponse(credencial);
    }

    @Override
    @Transactional(readOnly = true)
    public CredencialResponse obtenerMiCredencialPorEmail(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con email: " + email
                ));

        CredencialQr credencial = credencialQrRepository
                .findByIdUsuarioAndVigenteTrueAndEstadoRegistro(usuario.getId(), 1)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró credencial vigente"
                ));

        return mapToResponse(credencial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CredencialResponse> listarPorUsuario(Integer idUsuario) {

        List<CredencialQr> lista = credencialQrRepository
                .findByIdUsuarioAndEstadoRegistroOrderByFechaEmisionDesc(idUsuario, 1);

        return lista.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidacionQrResponse validarPorCodigo(String codigoQr) {
        ValidacionQrResponse response = new ValidacionQrResponse();

        return credencialQrRepository.findByCodigoQrAndEstadoRegistro(codigoQr, 1)
                .map(credencial -> {
                    Usuario u = usuarioRepository.findById(credencial.getIdUsuario())
                            .orElse(null);

                    boolean credVigente = Boolean.TRUE.equals(credencial.getVigente())
                            && (credencial.getFechaVencimiento() == null
                            || !credencial.getFechaVencimiento().isBefore(LocalDate.now()));

                    response.setValida(credVigente);
                    response.setMensaje(credVigente
                            ? "Credencial válida"
                            : "Credencial vencida o no vigente");

                    if (u != null) {
                        response.setIdUsuario(u.getId());
                        response.setNombres(u.getNombres());
                        response.setApellidos(u.getApellidos());
                        response.setEstadoUsuario(u.getEstado());
                    }

                    response.setTipoCredencial(credencial.getTipoCredencial());
                    response.setFechaEmision(credencial.getFechaEmision());
                    response.setFechaVencimiento(credencial.getFechaVencimiento());
                    response.setVigente(credencial.getVigente());

                    return response;
                })
                .orElseGet(() -> {
                    response.setValida(false);
                    response.setMensaje("Código QR no encontrado");
                    return response;
                });
    }

    private CredencialResponse mapToResponse(CredencialQr c) {
        CredencialResponse dto = new CredencialResponse();
        dto.setIdCredencial(c.getId());
        dto.setCodigoQr(c.getCodigoQr());
        dto.setTipoCredencial(c.getTipoCredencial());
        dto.setFechaEmision(c.getFechaEmision());
        dto.setFechaVencimiento(c.getFechaVencimiento());
        dto.setVigente(c.getVigente());

        // Recuperar datos de usuario para la respuesta
        usuarioRepository.findById(c.getIdUsuario())
                .ifPresent(u -> {
                    dto.setIdUsuario(u.getId());
                    dto.setNombres(u.getNombres());
                    dto.setApellidos(u.getApellidos());
                    dto.setEmail(u.getEmail());
                });

        return dto;
    }
}