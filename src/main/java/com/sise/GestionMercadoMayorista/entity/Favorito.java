package com.sise.GestionMercadoMayorista.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_favorito_usuario_stand", columnNames = {"id_cliente", "id_stand"})
        })
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorito")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stand", nullable = false)
    private Stand stand;

    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;

    @Column(name = "estado_registro")
    private Integer estadoRegistro;

    public Favorito() {
    }

    @PrePersist
    public void prePersist() {
        this.fechaAgregado = LocalDateTime.now();
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

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}
