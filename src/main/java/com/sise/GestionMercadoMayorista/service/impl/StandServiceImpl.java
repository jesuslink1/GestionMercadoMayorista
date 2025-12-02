package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.stand.StandRequestDto;
import com.sise.GestionMercadoMayorista.dto.stand.StandResponseDto;
import com.sise.GestionMercadoMayorista.entity.CategoriaStand;
import com.sise.GestionMercadoMayorista.entity.Stand;
import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.CategoriaStandRepository;
import com.sise.GestionMercadoMayorista.repository.StandRepository;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.StandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StandServiceImpl implements StandService {

    private final StandRepository standRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaStandRepository categoriaStandRepository;

    public StandServiceImpl(StandRepository standRepository,
                            UsuarioRepository usuarioRepository,
                            CategoriaStandRepository categoriaStandRepository) {
        this.standRepository = standRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaStandRepository = categoriaStandRepository;
    }

    // Listar todo
    @Override
    public List<StandResponseDto> listarTodosActivos() {
        List<Stand> lista = standRepository.findAll();

        return lista.stream()
                .filter(s -> s.getEstadoRegistro() == null || s.getEstadoRegistro() == 1)
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Listar con filtros
    @Override
    public List<StandResponseDto> listarConFiltros(String bloque,
                                                   Integer idCategoriaStand,
                                                   String estado) {

        String estadoUpper = (estado == null || estado.isBlank())
                ? null
                : estado.toUpperCase(Locale.ROOT);

        List<Stand> lista = standRepository.buscarConFiltros(
                bloque,
                idCategoriaStand,
                estadoUpper
        );

        return lista.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Listar para Socio
    @Override
    public List<StandResponseDto> listarPorPropietario(Integer idPropietario) {
        List<Stand> lista = standRepository
                .findByPropietarioIdAndEstadoRegistro(idPropietario, 1);

        return lista.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Obtener por ID
    @Override
    public StandResponseDto obtenerPorId(Integer id) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado con id: " + id));

        if (stand.getEstadoRegistro() != null && stand.getEstadoRegistro() == 0) {
            throw new IllegalArgumentException("Stand está eliminado lógicamente");
        }

        return mapToResponseDto(stand);
    }

    // Crear
    @Override
    public StandResponseDto crear(StandRequestDto request) {
        Stand stand = new Stand();

        // Propietario (puede ser opcional)
        if (request.getIdPropietario() != null) {
            Usuario propietario = usuarioRepository.findById(request.getIdPropietario())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Propietario no encontrado con id: " + request.getIdPropietario()));
            stand.setPropietario(propietario);
        }

        // Categoría stand
        if (request.getIdCategoriaStand() != null) {
            CategoriaStand categoriaStand = categoriaStandRepository.findById(request.getIdCategoriaStand())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Categoría de stand no encontrada con id: " + request.getIdCategoriaStand()));
            stand.setCategoriaStand(categoriaStand);
        }

        stand.setBloque(request.getBloque());
        stand.setNumeroStand(request.getNumeroStand());
        stand.setNombreComercial(request.getNombreComercial());
        stand.setDescripcionNegocio(request.getDescripcionNegocio());
        stand.setLatitud(request.getLatitud());
        stand.setLongitud(request.getLongitud());

        // Por defecto ABIERTO
        stand.setEstado("ABIERTO");
        stand.setEstadoRegistro(1);

        Stand guardado = standRepository.save(stand);
        return mapToResponseDto(guardado);
    }

    // Actualizar
    @Override
    public StandResponseDto actualizar(Integer id, StandRequestDto request) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado con id: " + id));

        if (stand.getEstadoRegistro() != null && stand.getEstadoRegistro() == 0) {
            throw new IllegalArgumentException("No se puede actualizar un stand eliminado lógicamente");
        }

        if (request.getIdPropietario() != null) {
            Optional<Usuario> propietarioOpt = usuarioRepository.findById(request.getIdPropietario());
            propietarioOpt.ifPresent(stand::setPropietario);
        }

        if (request.getIdCategoriaStand() != null) {
            Optional<CategoriaStand> catOpt = categoriaStandRepository.findById(request.getIdCategoriaStand());
            catOpt.ifPresent(stand::setCategoriaStand);
        }

        stand.setBloque(request.getBloque());
        stand.setNumeroStand(request.getNumeroStand());
        stand.setNombreComercial(request.getNombreComercial());
        stand.setDescripcionNegocio(request.getDescripcionNegocio());
        stand.setLatitud(request.getLatitud());
        stand.setLongitud(request.getLongitud());

        Stand actualizado = standRepository.save(stand);
        return mapToResponseDto(actualizado);
    }

    @Override
    public void cambiarEstado(Integer id, String nuevoEstado) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado con id: " + id));

        if (stand.getEstadoRegistro() != null && stand.getEstadoRegistro() == 0) {
            throw new IllegalArgumentException("No se puede cambiar estado de un stand eliminado lógicamente");
        }

        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("Estado no puede ser vacío");
        }

        stand.setEstado(nuevoEstado.toUpperCase(Locale.ROOT));
        standRepository.save(stand);
    }

    // Eliminación lógica
    @Override
    public void eliminarLogico(Integer id) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stand no encontrado con id: " + id));

        stand.setEstado("CERRADO");
        stand.setEstadoRegistro(0);
        standRepository.save(stand);
    }

    // Mapeos
    private StandResponseDto mapToResponseDto(Stand stand) {
        StandResponseDto dto = new StandResponseDto();
        dto.setId(stand.getId());
        dto.setBloque(stand.getBloque());
        dto.setNumeroStand(stand.getNumeroStand());
        dto.setNombreComercial(stand.getNombreComercial());
        dto.setDescripcionNegocio(stand.getDescripcionNegocio());
        dto.setLatitud(stand.getLatitud());
        dto.setLongitud(stand.getLongitud());
        dto.setEstado(stand.getEstado());

        if (stand.getPropietario() != null) {
            dto.setIdPropietario(stand.getPropietario().getId());
            String nombreCompleto =
                    (stand.getPropietario().getNombres() != null ? stand.getPropietario().getNombres() : "") +
                            " " +
                            (stand.getPropietario().getApellidos() != null ? stand.getPropietario().getApellidos() : "");
            dto.setNombrePropietario(nombreCompleto.trim());
        }

        if (stand.getCategoriaStand() != null) {
            dto.setIdCategoriaStand(stand.getCategoriaStand().getId());
            dto.setNombreCategoriaStand(stand.getCategoriaStand().getNombre());
        }

        return dto;
    }
}