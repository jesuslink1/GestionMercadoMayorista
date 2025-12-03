package com.sise.GestionMercadoMayorista.dto.calificacion;

public class CalificacionCrearRequestDto {

    private Integer idStand;          // obligatorio
    private Integer puntuacion;       // 1..5
    private String comentario;

    // Datos para anónimo (si no está logueado)
    private String nombreAnonimo;
    private String telefonoAnonimo;
    private String emailAnonimo;

    // getters y setters


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getIdStand() {
        return idStand;
    }

    public void setIdStand(Integer idStand) {
        this.idStand = idStand;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombreAnonimo() {
        return nombreAnonimo;
    }

    public void setNombreAnonimo(String nombreAnonimo) {
        this.nombreAnonimo = nombreAnonimo;
    }

    public String getTelefonoAnonimo() {
        return telefonoAnonimo;
    }

    public void setTelefonoAnonimo(String telefonoAnonimo) {
        this.telefonoAnonimo = telefonoAnonimo;
    }

    public String getEmailAnonimo() {
        return emailAnonimo;
    }

    public void setEmailAnonimo(String emailAnonimo) {
        this.emailAnonimo = emailAnonimo;
    }
}
