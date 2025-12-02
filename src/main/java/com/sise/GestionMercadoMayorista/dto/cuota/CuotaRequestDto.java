package com.sise.GestionMercadoMayorista.dto.cuota;

import java.math.BigDecimal;

public class CuotaRequestDto {
    private String periodo;          // "2025-11"
    private String fechaVencimiento; // "2025-11-30"
    private BigDecimal montoCuota;

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
}