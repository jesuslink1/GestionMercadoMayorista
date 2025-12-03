package com.sise.GestionMercadoMayorista.dto.favorito;

public class FavoritoResponseDto {

    private Integer idFavorito;
    private Integer idStand;
    private String nombreStand;
    private String bloque;
    private String numeroStand;
    private String categoriaStand;
    private String fechaAgregado;

    // getters y setters

    public Integer getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(Integer idFavorito) {
        this.idFavorito = idFavorito;
    }

    public Integer getIdStand() {
        return idStand;
    }

    public void setIdStand(Integer idStand) {
        this.idStand = idStand;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getNombreStand() {
        return nombreStand;
    }

    public void setNombreStand(String nombreStand) {
        this.nombreStand = nombreStand;
    }

    public String getNumeroStand() {
        return numeroStand;
    }

    public void setNumeroStand(String numeroStand) {
        this.numeroStand = numeroStand;
    }

    public String getCategoriaStand() {
        return categoriaStand;
    }

    public void setCategoriaStand(String categoriaStand) {
        this.categoriaStand = categoriaStand;
    }

    public String getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(String fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}