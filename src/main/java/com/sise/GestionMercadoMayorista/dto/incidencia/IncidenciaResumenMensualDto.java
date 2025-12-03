package com.sise.GestionMercadoMayorista.dto.incidencia;

public class IncidenciaResumenMensualDto {

    private int anio;
    private int mes;

    private long totalAbiertas;
    private long totalEnProceso;
    private long totalResueltas;
    private long totalCerradas;

    // getters y setters

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public long getTotalAbiertas() {
        return totalAbiertas;
    }

    public void setTotalAbiertas(long totalAbiertas) {
        this.totalAbiertas = totalAbiertas;
    }

    public long getTotalEnProceso() {
        return totalEnProceso;
    }

    public void setTotalEnProceso(long totalEnProceso) {
        this.totalEnProceso = totalEnProceso;
    }

    public long getTotalResueltas() {
        return totalResueltas;
    }

    public void setTotalResueltas(long totalResueltas) {
        this.totalResueltas = totalResueltas;
    }

    public long getTotalCerradas() {
        return totalCerradas;
    }

    public void setTotalCerradas(long totalCerradas) {
        this.totalCerradas = totalCerradas;
    }
}