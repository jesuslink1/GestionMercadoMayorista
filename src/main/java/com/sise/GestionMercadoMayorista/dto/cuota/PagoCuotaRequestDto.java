package com.sise.GestionMercadoMayorista.dto.cuota;

import java.math.BigDecimal;

public class PagoCuotaRequestDto {
    private BigDecimal montoPagado;
    private String medioPago;       // EFECTIVO, YAPE, etc.
    private String referenciaPago;  // nro operación
    private String fechaPago;       // opcional, "2025-11-30"
    private String observaciones;   // opcional

    // getters y setters
    public BigDecimal getMontoPagado() {
        return montoPagado;
    }
    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }
    public String getMedioPago() {
        return medioPago;
    }
    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
    public String getReferenciaPago() {
        return referenciaPago;
    }
    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }
    public String getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}