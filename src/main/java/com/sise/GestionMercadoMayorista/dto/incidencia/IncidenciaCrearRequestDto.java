package com.sise.GestionMercadoMayorista.dto.incidencia;

public class IncidenciaCrearRequestDto {

    private Integer idStand;        // stand donde ocurre el problema
    private String titulo;
    private String descripcion;
    private String tipo;            // LUZ, DESAGUE, LIMPIEZA, ...
    private String prioridad;       // BAJA, MEDIA, ALTA
    private String fotoUrl;         // opcional

    // getters y setters


    public Integer getIdStand() {
        return idStand;
    }

    public void setIdStand(Integer idStand) {
        this.idStand = idStand;
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

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
