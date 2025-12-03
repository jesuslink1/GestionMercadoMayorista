package com.sise.GestionMercadoMayorista.dto.incidencia;

public class IncidenciaResponseDto {

    private Integer idIncidencia;

    private Integer idStand;
    private String nombreStand;
    private String bloque;
    private String numeroStand;

    private Integer idReportante;
    private String nombreReportante;

    private Integer idResponsable;
    private String nombreResponsable;

    private String titulo;
    private String descripcion;
    private String tipo;
    private String prioridad;
    private String estado;

    private String fotoUrl;
    private String fechaReporte;   // String ISO en el DTO
    private String fechaCierre;

    // getters y setters

    public Integer getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(Integer idIncidencia) {
        this.idIncidencia = idIncidencia;
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

    public Integer getIdReportante() {
        return idReportante;
    }

    public void setIdReportante(Integer idReportante) {
        this.idReportante = idReportante;
    }

    public String getNumeroStand() {
        return numeroStand;
    }

    public void setNumeroStand(String numeroStand) {
        this.numeroStand = numeroStand;
    }

    public String getNombreReportante() {
        return nombreReportante;
    }

    public void setNombreReportante(String nombreReportante) {
        this.nombreReportante = nombreReportante;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(String fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}