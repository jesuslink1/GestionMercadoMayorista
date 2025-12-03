package com.sise.GestionMercadoMayorista.dto.credencial;

import java.time.LocalDate;

public class ValidacionQrResponse {

    private boolean valida;
    private String mensaje;

    // Datos básicos de la persona
    private Integer idUsuario;
    private String nombres;
    private String apellidos;
    private String estadoUsuario; // ACTIVO / BAJA / SUSPENDIDO

    // Datos de la credencial
    private String tipoCredencial;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private Boolean vigente;

    // Getters y setters

    public boolean isValida() {
        return valida;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getTipoCredencial() {
        return tipoCredencial;
    }

    public void setTipoCredencial(String tipoCredencial) {
        this.tipoCredencial = tipoCredencial;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }
}
