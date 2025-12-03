package com.sise.GestionMercadoMayorista.dto.credencial;

import java.time.LocalDate;

public class CredencialResponse {

    private Integer idCredencial;
    private Integer idUsuario;
    private String nombres;
    private String apellidos;
    private String email;

    private String codigoQr;
    private String tipoCredencial;

    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private Boolean vigente;

    // Getters y setters

    public Integer getIdCredencial() {
        return idCredencial;
    }

    public void setIdCredencial(Integer idCredencial) {
        this.idCredencial = idCredencial;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
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
