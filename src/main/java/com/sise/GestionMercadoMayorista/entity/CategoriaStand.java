package com.sise.GestionMercadoMayorista.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias_stands")
public class CategoriaStand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_stand")
    private Integer id;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "color_hex", length = 7)
    private String colorHex;

    @Column(name = "icono_url", length = 255)
    private String iconoUrl;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "estado_registro")
    private Integer estadoRegistro;

    public CategoriaStand() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getIconoUrl() {
        return iconoUrl;
    }

    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}