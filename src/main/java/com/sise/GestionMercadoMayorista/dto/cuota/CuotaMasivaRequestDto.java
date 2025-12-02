package com.sise.GestionMercadoMayorista.dto.cuota;

import java.math.BigDecimal;

public class CuotaMasivaRequestDto {
    private String periodo;
    private String fechaVencimiento;
    private BigDecimal montoCuota;

    private String bloque;           // opcional
    private Integer idCategoriaStand; // opcional

    // getters y setters
    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public BigDecimal getMontoCuota() {
        return montoCuota;
    }
    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }
    public String getBloque() {
        return bloque;
    }
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
    public Integer getIdCategoriaStand() {
        return idCategoriaStand;
    }
    public void setIdCategoriaStand(Integer idCategoriaStand) {
        this.idCategoriaStand = idCategoriaStand;
    }
}