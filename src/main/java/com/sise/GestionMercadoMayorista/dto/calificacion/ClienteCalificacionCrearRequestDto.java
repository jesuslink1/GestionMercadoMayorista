package com.sise.GestionMercadoMayorista.dto.calificacion;

public class ClienteCalificacionCrearRequestDto {

    private Integer idStand;
    private Integer puntuacion;
    private String comentario;

    public Integer getIdStand() { return idStand; }
    public void setIdStand(Integer idStand) { this.idStand = idStand; }

    public Integer getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
