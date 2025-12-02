package com.sise.GestionMercadoMayorista.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stand")
    private Stand stand; // puede ser null (incidencia general)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reportante", nullable = false)
    private Usuario reportante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable")
    private Usuario responsable;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "prioridad", length = 20)
    private String prioridad;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "estado_registro")
    private Integer estadoRegistro;

    public Incidencia() {
    }

    @PrePersist
    public void prePersist() {
        this.fechaReporte = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "ABIERTA";
        }
        if (this.estadoRegistro == null) {
            this.estadoRegistro = 1;
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public Usuario getReportante() {
        return reportante;
    }

    public void setReportante(Usuario reportante) {
        this.reportante = reportante;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}
