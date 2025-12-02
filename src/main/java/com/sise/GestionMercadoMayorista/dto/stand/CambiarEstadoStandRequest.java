package com.sise.GestionMercadoMayorista.dto.stand;

public class CambiarEstadoStandRequest {

    private String estado; // ABIERTO, CERRADO, CLAUSURADO

    public CambiarEstadoStandRequest() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
