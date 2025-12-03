package com.sise.GestionMercadoMayorista.dto.calificacion;

public class PromedioCalificacionDto {

    private Integer idStand;
    private String nombreStand;

    private double promedio;
    private long totalCalificaciones;

    // getters y setters

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

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public long getTotalCalificaciones() {
        return totalCalificaciones;
    }

    public void setTotalCalificaciones(long totalCalificaciones) {
        this.totalCalificaciones = totalCalificaciones;
    }
}