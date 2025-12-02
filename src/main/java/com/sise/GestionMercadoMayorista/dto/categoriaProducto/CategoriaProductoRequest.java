package com.sise.GestionMercadoMayorista.dto.categoriaProducto;

public class CategoriaProductoRequest {
    private String nombre;
    private String descripcion;
    private Boolean estado;

    public CategoriaProductoRequest() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}