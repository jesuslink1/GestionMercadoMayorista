package com.sise.GestionMercadoMayorista.dto.calificacion;

public class CalificacionResponseDto {

    private Integer idCalificacion;
    private Integer idStand;
    private String nombreStand;

    private Integer puntuacion;
    private String comentario;
    private String fecha;

    private Integer idCliente;
    private String nombreCliente;

    private String nombreAnonimo;
    private String origen;

    // getters y setters

    public Integer getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Integer getIdStand() {
        return idStand;
    }

    public void setIdStand(Integer idStand) {
        this.idStand = idStand;
    }

    public String getNombreStand() {
        return nombreStand;
    }

    public void setNombreStand(String nombreStand) {
        this.nombreStand = nombreStand;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreAnonimo() {
        return nombreAnonimo;
    }

    public void setNombreAnonimo(String nombreAnonimo) {
        this.nombreAnonimo = nombreAnonimo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
