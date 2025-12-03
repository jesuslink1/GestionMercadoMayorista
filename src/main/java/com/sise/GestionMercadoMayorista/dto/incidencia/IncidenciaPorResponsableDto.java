package com.sise.GestionMercadoMayorista.dto.incidencia;

public class IncidenciaPorResponsableDto {

    private Integer idResponsable;
    private String nombreResponsable;
    private long totalAbiertas;
    private long totalResueltas;
    private long totalEnProceso;
    private long totalCerradas;

    // getters y setters

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

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
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
