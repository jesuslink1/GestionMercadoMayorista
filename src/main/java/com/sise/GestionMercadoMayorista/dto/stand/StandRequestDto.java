package com.sise.GestionMercadoMayorista.dto.stand;

public class StandRequestDto {

    private Integer idPropietario;       // id_usuario
    private Integer idCategoriaStand;    // id_categoria_stand

    private String bloque;
    private String numeroStand;
    private String nombreComercial;
    private String descripcionNegocio;

    private Double latitud;
    private Double longitud;

    public StandRequestDto() {
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Integer getIdCategoriaStand() {
        return idCategoriaStand;
    }

    public void setIdCategoriaStand(Integer idCategoriaStand) {
        this.idCategoriaStand = idCategoriaStand;
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
}