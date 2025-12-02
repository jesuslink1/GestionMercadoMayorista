package com.sise.GestionMercadoMayorista.dto.producto;

import java.math.BigDecimal;

public class ProductoResponseDto {

    private Integer idProducto;
    private Integer idStand;
    private Integer idCategoriaProducto;
    private String nombreCategoriaProducto;

    private String nombre;
    private String descripcion;
    private String unidadMedida;
    private String imagenUrl;

    private BigDecimal precioActual;
    private Boolean enOferta;
    private BigDecimal precioOferta;
    private Boolean visibleDirectorio;

    private String bloqueStand;
    private String numeroStand;
    private String nombreComercialStand;

    public ProductoResponseDto() {
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdStand() {
        return idStand;
    }

    public void setIdStand(Integer idStand) {
        this.idStand = idStand;
    }

    public Integer getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setIdCategoriaProducto(Integer idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    public String getNombreCategoriaProducto() {
        return nombreCategoriaProducto;
    }

    public void setNombreCategoriaProducto(String nombreCategoriaProducto) {
        this.nombreCategoriaProducto = nombreCategoriaProducto;
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

    public String getBloqueStand() {
        return bloqueStand;
    }

    public void setBloqueStand(String bloqueStand) {
        this.bloqueStand = bloqueStand;
    }

    public String getNumeroStand() {
        return numeroStand;
    }

    public void setNumeroStand(String numeroStand) {
        this.numeroStand = numeroStand;
    }

    public String getNombreComercialStand() {
        return nombreComercialStand;
    }

    public void setNombreComercialStand(String nombreComercialStand) {
        this.nombreComercialStand = nombreComercialStand;
    }
}
