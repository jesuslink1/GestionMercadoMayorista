package com.sise.GestionMercadoMayorista.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stands",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_stand_bloque_num", columnNames = {"bloque", "numero_stand"})
        })
public class Stand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stand")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario")
    private Usuario propietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria_stand")
    private CategoriaStand categoriaStand;

    @Column(name = "bloque", length = 10, nullable = false)
    private String bloque;

    @Column(name = "numero_stand", length = 10, nullable = false)
    private String numeroStand;

    @Column(name = "nombre_comercial", length = 120)
    private String nombreComercial;

    @Column(name = "descripcion_negocio")
    private String descripcionNegocio;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "estado_registro")
    private Integer estadoRegistro;

    public Stand() {
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.estado == null) {
            this.estado = "ABIERTO";
        }
        if (this.estadoRegistro == null) {
            this.estadoRegistro = 1;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public CategoriaStand getCategoriaStand() {
        return categoriaStand;
    }

    public void setCategoriaStand(CategoriaStand categoriaStand) {
        this.categoriaStand = categoriaStand;
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

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDescripcionNegocio() {
        return descripcionNegocio;
    }

    public void setDescripcionNegocio(String descripcionNegocio) {
        this.descripcionNegocio = descripcionNegocio;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}