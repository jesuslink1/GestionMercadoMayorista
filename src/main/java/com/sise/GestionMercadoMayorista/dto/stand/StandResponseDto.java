package com.sise.GestionMercadoMayorista.dto.stand;

public class StandResponseDto {

    private Integer id;
    private Integer idPropietario;
    private String nombrePropietario;

    private Integer idCategoriaStand;
    private String nombreCategoriaStand;

    private String bloque;
    private String numeroStand;
    private String nombreComercial;
    private String descripcionNegocio;

    private Double latitud;
    private Double longitud;

    private String estado;

    public StandResponseDto() {
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public Integer getIdCategoriaStand() {
        return idCategoriaStand;
    }

    public void setIdCategoriaStand(Integer idCategoriaStand) {
        this.idCategoriaStand = idCategoriaStand;
    }

    public String getNombreCategoriaStand() {
        return nombreCategoriaStand;
    }

    public void setNombreCategoriaStand(String nombreCategoriaStand) {
        this.nombreCategoriaStand = nombreCategoriaStand;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getNumeroStand() {
        return numeroStand;
    }

    public void setNumeroStand(String numeroStand) {
        this.numeroStand = numeroStand;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDescripcionNegocio() {
        return descripcionNegocio;
    }

    public void setDescripcionNegocio(String descripcionNegocio) {
        this.descripcionNegocio = descripcionNegocio;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
