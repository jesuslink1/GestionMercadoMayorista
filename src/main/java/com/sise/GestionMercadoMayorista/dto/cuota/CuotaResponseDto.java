package com.sise.GestionMercadoMayorista.dto.cuota;

import java.math.BigDecimal;

public class CuotaResponseDto {

    private Integer idCuota;

    private Integer idStand;
    private String nombreStand;
    private String bloque;
    private String numeroStand;

    private String periodo;
    private String fechaVencimiento;

    private BigDecimal montoCuota;
    private BigDecimal montoPagado;
    private BigDecimal saldoPendiente;
    private String estado;

    private String medioPago;
    private String referenciaPago;
    private String fechaPago;

    // getters y setters
    public Integer getIdCuota() {
        return idCuota;
    }
    public void setIdCuota(Integer idCuota) {
        this.idCuota = idCuota;
    }
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
    public String getBloque() {
        return bloque;
    }
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
    public String getNumeroStand() {
        return numeroStand;
    }
    public void setNumeroStand(String numeroStand) {
        this.numeroStand = numeroStand;
    }
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
    public BigDecimal getMontoPagado() {
        return montoPagado;
    }
    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }
    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }
    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
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
}