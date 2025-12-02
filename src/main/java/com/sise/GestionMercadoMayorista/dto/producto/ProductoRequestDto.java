package com.sise.GestionMercadoMayorista.dto.producto;

import java.math.BigDecimal;

public class ProductoRequestDto {

    private Integer idCategoriaProducto;
    private String nombre;
    private String descripcion;
    private String unidadMedida;
    private String imagenUrl;
    private BigDecimal precioActual;
    private Boolean enOferta;
    private BigDecimal precioOferta;
    private Boolean visibleDirectorio;

    public ProductoRequestDto() {
    }

    public Integer getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setIdCategoriaProducto(Integer idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public BigDecimal getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(BigDecimal precioActual) {
        this.precioActual = precioActual;
    }

    public Boolean getEnOferta() {
        return enOferta;
    }

    public void setEnOferta(Boolean enOferta) {
        this.enOferta = enOferta;
    }

    public BigDecimal getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(BigDecimal precioOferta) {
        this.precioOferta = precioOferta;
    }

    public Boolean getVisibleDirectorio() {
        return visibleDirectorio;
    }

    public void setVisibleDirectorio(Boolean visibleDirectorio) {
        this.visibleDirectorio = visibleDirectorio;
    }
}
