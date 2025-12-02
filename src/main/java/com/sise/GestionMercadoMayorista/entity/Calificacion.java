package com.sise.GestionMercadoMayorista.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_calificacion_usuario_stand", columnNames = {"id_cliente", "id_stand"})
        })
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Usuario cliente; // puede ser null si es anónimo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stand", nullable = false)
    private Stand stand;

    @Column(name = "puntuacion", nullable = false)
    private Integer puntuacion;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "nombre_anonimo", length = 100)
    private String nombreAnonimo;

    @Column(name = "telefono_anonimo", length = 20)
    private String telefonoAnonimo;

    @Column(name = "email_anonimo", length = 100)
    private String emailAnonimo;

    @Column(name = "origen", length = 20)
    private String origen;

    @Column(name = "estado_registro")
    private Integer estadoRegistro;

    public Calificacion() {
    }

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
        if (this.origen == null) {
            this.origen = "ANONIMO";
        }
        if (this.estadoRegistro == null) {
            this.estadoRegistro = 1;
        }
    }

    // Getters/setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNombreAnonimo() {
        return nombreAnonimo;
    }

    public void setNombreAnonimo(String nombreAnonimo) {
        this.nombreAnonimo = nombreAnonimo;
    }

    public String getTelefonoAnonimo() {
        return telefonoAnonimo;
    }

    public void setTelefonoAnonimo(String telefonoAnonimo) {
        this.telefonoAnonimo = telefonoAnonimo;
    }

    public String getEmailAnonimo() {
        return emailAnonimo;
    }

    public void setEmailAnonimo(String emailAnonimo) {
        this.emailAnonimo = emailAnonimo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}