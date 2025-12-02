package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.dto.producto.ProductoRequestDto;
import com.sise.GestionMercadoMayorista.dto.producto.ProductoResponseDto;
import com.sise.GestionMercadoMayorista.entity.CategoriaProducto;
import com.sise.GestionMercadoMayorista.entity.Producto;
import com.sise.GestionMercadoMayorista.entity.Stand;
import com.sise.GestionMercadoMayorista.repository.CategoriaProductoRepository;
import com.sise.GestionMercadoMayorista.repository.ProductoRepository;
import com.sise.GestionMercadoMayorista.repository.StandRepository;
import com.sise.GestionMercadoMayorista.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final StandRepository standRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                               StandRepository standRepository,
                               CategoriaProductoRepository categoriaProductoRepository) {
        this.productoRepository = productoRepository;
        this.standRepository = standRepository;
        this.categoriaProductoRepository = categoriaProductoRepository;
    }

    // =====================================================
    // MÉTODOS PARA SOCIO
    // =====================================================

    @Override
    public ProductoResponseDto crearProductoEnMiStand(Integer idStand, ProductoRequestDto dto) {
        String email = obtenerEmailUsuarioActual();

        // Verificar que el stand le pertenece al socio (consulta custom en StandRepository)
        Stand stand = standRepository
                .findByIdAndPropietarioEmailAndEstadoRegistro(idStand, email, 1)
                .orElseThrow(() -> new IllegalArgumentException("No tienes permisos sobre este stand."));

        CategoriaProducto categoria = categoriaProductoRepository
                .findById(dto.getIdCategoriaProducto())
                .orElseThrow(() -> new IllegalArgumentException("Categoría de producto no encontrada."));

        validarPrecioYOferta(dto.getPrecioActual(), dto.getEnOferta(), dto.getPrecioOferta());

        Producto producto = new Producto();
        producto.setStand(stand);
        producto.setCategoriaProducto(categoria);
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setUnidadMedida(dto.getUnidadMedida());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setPrecioActual(dto.getPrecioActual());
        producto.setEnOferta(dto.getEnOferta() != null ? dto.getEnOferta() : Boolean.FALSE);
        producto.setPrecioOferta(dto.getPrecioOferta());
        producto.setVisibleDirectorio(dto.getVisibleDirectorio() != null ? dto.getVisibleDirectorio() : Boolean.TRUE);

        Producto guardado = productoRepository.save(producto);
        return mapearProductoAResponseDto(guardado);
    }

    @Override
    public ProductoResponseDto actualizarProducto(Integer idProducto, ProductoRequestDto dto) {
        Authentication auth = obtenerAuthenticationActual();
        String email = auth.getName();

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

        // Verificar que el producto pertenece a un stand del socio
        if (!esAdminOSupervisor(auth)) {
            if (!producto.getStand().getPropietario().getEmail().equals(email)) {
                throw new IllegalArgumentException("No tienes permisos para editar este producto.");
            }
        }

        CategoriaProducto categoria = categoriaProductoRepository
                .findById(dto.getIdCategoriaProducto())
                .orElseThrow(() -> new IllegalArgumentException("Categoría de producto no encontrada."));

        validarPrecioYOferta(dto.getPrecioActual(), dto.getEnOferta(), dto.getPrecioOferta());

        producto.setCategoriaProducto(categoria);
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setUnidadMedida(dto.getUnidadMedida());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setPrecioActual(dto.getPrecioActual());
        producto.setEnOferta(dto.getEnOferta() != null ? dto.getEnOferta() : Boolean.FALSE);
        producto.setPrecioOferta(dto.getPrecioOferta());
        if (dto.getVisibleDirectorio() != null) {
            producto.setVisibleDirectorio(dto.getVisibleDirectorio());
        }

        Producto actualizado = productoRepository.save(producto);
        return mapearProductoAResponseDto(actualizado);
    }

    @Override
    public void cambiarVisibilidad(Integer idProducto, boolean visible) {
        Authentication auth = obtenerAuthenticationActual();
        String email = auth.getName();

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

        // Si no es ADMIN ni SUPERVISOR, validar que sea dueño del stand
        if (!esAdminOSupervisor(auth)) {
            if (!producto.getStand().getPropietario().getEmail().equals(email)) {
                throw new IllegalArgumentException("No tienes permisos para modificar este producto.");
            }
        }

        producto.setVisibleDirectorio(visible);
        productoRepository.save(producto);
    }

    @Override
    public void eliminarLogico(Integer idProducto) {
        Authentication auth = obtenerAuthenticationActual();
        String email = auth.getName();

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

        // Si no es ADMIN ni SUPERVISOR, validar que sea dueño
        if (!esAdminOSupervisor(auth)) {
            if (!producto.getStand().getPropietario().getEmail().equals(email)) {
                throw new IllegalArgumentException("No tienes permisos para eliminar este producto.");
            }
        }

        producto.setEstadoRegistro(0);
        productoRepository.save(producto);
    }

    @Override
    public List<ProductoResponseDto> listarProductosDeMiStand(Integer idStand) {
        String email = obtenerEmailUsuarioActual();

        List<Producto> productos = productoRepository.findByStandAndPropietario(idStand, email);
        List<ProductoResponseDto> dtos = new ArrayList<>();

        for (Producto p : productos) {
            dtos.add(mapearProductoAResponseDto(p));
        }

        return dtos;
    }

    // =====================================================
    // MÉTODOS PARA ADMIN / SUPERVISOR (AUDITORÍA)
    // =====================================================

    @Override
    public List<ProductoResponseDto> listarProductosParaAuditoria(
            String nombre,
            Integer idCategoriaProducto,
            String bloque,
            Boolean visible,
            Integer estadoRegistro
    ) {
        // Normalizar el parámetro nombre en Java
        String patronNombre = null;
        if (nombre != null) {
            nombre = nombre.trim();
            if (!nombre.isEmpty()) {
                patronNombre = "%" + nombre.toLowerCase() + "%";
            }
        }

        List<Producto> productos = productoRepository.buscarParaAuditoria(
                patronNombre,
                idCategoriaProducto,
                bloque,
                visible,
                estadoRegistro
        );

        return productos.stream()
                .map(this::mapearProductoAResponseDto)
                .toList();
    }

    @Override
    public ProductoResponseDto obtenerProductoPorId(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        return mapearProductoAResponseDto(producto);
    }

    // =====================================================
    // HELPERS PRIVADOS
    // =====================================================

    private Authentication obtenerAuthenticationActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalArgumentException("No se pudo obtener el usuario autenticado.");
        }
        return auth;
    }

    private String obtenerEmailUsuarioActual() {
        return obtenerAuthenticationActual().getName(); // asumo que es el email
    }

    private boolean esAdminOSupervisor(Authentication auth) {
        if (auth == null) {
            return false;
        }
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String rol = authority.getAuthority();
            if ("ROLE_ADMIN".equals(rol) || "ROLE_SUPERVISOR".equals(rol)) {
                return true;
            }
        }
        return false;
    }

    private void validarPrecioYOferta(BigDecimal precioActual, Boolean enOferta, BigDecimal precioOferta) {
        if (precioActual == null || precioActual.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio actual debe ser mayor o igual a 0.");
        }
        if (Boolean.TRUE.equals(enOferta)) {
            if (precioOferta == null) {
                throw new IllegalArgumentException("Si el producto está en oferta, debes indicar un precio de oferta.");
            }
            if (precioOferta.compareTo(precioActual) > 0) {
                throw new IllegalArgumentException("El precio de oferta no puede ser mayor al precio actual.");
            }
        }
    }

    private ProductoResponseDto mapearProductoAResponseDto(Producto producto) {
        ProductoResponseDto dto = new ProductoResponseDto();

        // OJO: si tu entidad Producto no tiene getId() sino getIdProducto(),
        // cambia esta línea por: dto.setIdProducto(producto.getIdProducto());
        dto.setIdProducto(producto.getId());

        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setUnidadMedida(producto.getUnidadMedida());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setPrecioActual(producto.getPrecioActual());
        dto.setEnOferta(producto.getEnOferta());
        dto.setPrecioOferta(producto.getPrecioOferta());
        dto.setVisibleDirectorio(producto.getVisibleDirectorio());

        // Categoría del producto
        if (producto.getCategoriaProducto() != null) {
            dto.setIdCategoriaProducto(producto.getCategoriaProducto().getId());
            dto.setNombreCategoriaProducto(producto.getCategoriaProducto().getNombre());
        }

        // Datos del stand
        if (producto.getStand() != null) {
            dto.setIdStand(producto.getStand().getId()); // idem: cambia getId() si tu Stand usa otro nombre
            dto.setBloqueStand(producto.getStand().getBloque());
            dto.setNumeroStand(producto.getStand().getNumeroStand());
            dto.setNombreComercialStand(producto.getStand().getNombreComercial());
        }

        return dto;
    }
}