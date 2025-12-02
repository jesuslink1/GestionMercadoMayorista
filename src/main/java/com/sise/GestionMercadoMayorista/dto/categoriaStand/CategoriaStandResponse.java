package com.sise.GestionMercadoMayorista.dto.categoriaStand;

public class CategoriaStandResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String colorHex;
    private String iconoUrl;
    private Boolean estado;

    public CategoriaStandResponse() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    public String getIconoUrl() { return iconoUrl; }
    public void setIconoUrl(String iconoUrl) { this.iconoUrl = iconoUrl; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
