package com.sise.GestionMercadoMayorista.dto.cuota;

import java.util.List;

public class IndicadoresCuotasDto {

    private String periodo;

    private long totalStandsConCuota;
    private long standsAlDia;
    private long standsMorosos;

    private double porcentajeAlDia;
    private double porcentajeMorosos;

    public static class MorosidadBloqueDto {
        private String bloque;
        private long standsAlDia;
        private long standsMorosos;

        public String getBloque() {
            return bloque;
        }
        public void setBloque(String bloque) {
            this.bloque = bloque;
        }
        public long getStandsAlDia() {
            return standsAlDia;
        }
        public void setStandsAlDia(long standsAlDia) {
            this.standsAlDia = standsAlDia;
        }
        public long getStandsMorosos() {
            return standsMorosos;
        }
        public void setStandsMorosos(long standsMorosos) {
            this.standsMorosos = standsMorosos;
        }
    }

    private List<MorosidadBloqueDto> morosidadPorBloque;

    // getters y setters
    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    public long getTotalStandsConCuota() {
        return totalStandsConCuota;
    }
    public void setTotalStandsConCuota(long totalStandsConCuota) {
        this.totalStandsConCuota = totalStandsConCuota;
    }
    public long getStandsAlDia() {
        return standsAlDia;
    }
    public void setStandsAlDia(long standsAlDia) {
        this.standsAlDia = standsAlDia;
    }
    public long getStandsMorosos() {
        return standsMorosos;
    }
    public void setStandsMorosos(long standsMorosos) {
        this.standsMorosos = standsMorosos;
    }
    public double getPorcentajeAlDia() {
        return porcentajeAlDia;
    }
    public void setPorcentajeAlDia(double porcentajeAlDia) {
        this.porcentajeAlDia = porcentajeAlDia;
    }
    public double getPorcentajeMorosos() {
        return porcentajeMorosos;
    }
    public void setPorcentajeMorosos(double porcentajeMorosos) {
        this.porcentajeMorosos = porcentajeMorosos;
    }
    public List<MorosidadBloqueDto> getMorosidadPorBloque() {
        return morosidadPorBloque;
    }
    public void setMorosidadPorBloque(List<MorosidadBloqueDto> morosidadPorBloque) {
        this.morosidadPorBloque = morosidadPorBloque;
    }
}