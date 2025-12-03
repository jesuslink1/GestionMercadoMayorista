package com.sise.GestionMercadoMayorista.dto.credencial;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CrearCredencialRequest {

    @NotNull
    private Integer idUsuario;

    // Opcional: puedes dejarla null y por defecto +1 año
    @FutureOrPresent(message = "La fecha de vencimiento debe ser hoy o futura")
    private LocalDate fechaVencimiento;

    // Opcional: SOCIO / TRABAJADOR / VISITANTE, etc.
    private String tipoCredencial;

    // Getters y setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getTipoCredencial() {
        return tipoCredencial;
    }

    public void setTipoCredencial(String tipoCredencial) {
        this.tipoCredencial = tipoCredencial;
    }
}
