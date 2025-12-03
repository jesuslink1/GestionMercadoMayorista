package com.sise.GestionMercadoMayorista.dto.incidencia;

public class IncidenciaCambiarEstadoRequestDto {

    private String nuevoEstado;     // EN_PROCESO, RESUELTA, CERRADA

    // getters y setters

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
