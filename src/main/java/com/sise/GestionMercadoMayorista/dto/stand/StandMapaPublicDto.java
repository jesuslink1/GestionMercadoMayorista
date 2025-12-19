package com.sise.GestionMercadoMayorista.dto.stand;

public class StandMapaPublicDto {

    private Integer id;

    private String bloque;          // A, B, C, D...
    private String numeroStand;     // 101, 102...

    private String nombreComercial; // Si es null, mostramos "Disponible 101"
    private String rubro;           // nombreCategoriaStand o "---"

    /**
     * Estado del backend: ABIERTO, CERRADO, CLAUSURADO o DISPONIBLE
     */
    private String estado;
    private Integer idCategoriaStand;
    private String nombreCategoriaStand;
    private String categoriaColorHex;
    private String categoriaIconoUrl;

    public StandMapaPublicDto() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getBloque() { return bloque; }
    public void setBloque(String bloque) { this.bloque = bloque; }

    public String getNumeroStand() { return numeroStand; }
    public void setNumeroStand(String numeroStand) { this.numeroStand = numeroStand; }

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }

    public String getRubro() { return rubro; }
    public void setRubro(String rubro) { this.rubro = rubro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getIdCategoriaStand() { return idCategoriaStand; }
    public void setIdCategoriaStand(Integer idCategoriaStand) { this.idCategoriaStand = idCategoriaStand; }

    public String getNombreCategoriaStand() { return nombreCategoriaStand; }
    public void setNombreCategoriaStand(String nombreCategoriaStand) { this.nombreCategoriaStand = nombreCategoriaStand; }

    public String getCategoriaColorHex() { return categoriaColorHex; }
    public void setCategoriaColorHex(String categoriaColorHex) { this.categoriaColorHex = categoriaColorHex; }

    public String getCategoriaIconoUrl() { return categoriaIconoUrl; }
    public void setCategoriaIconoUrl(String categoriaIconoUrl) { this.categoriaIconoUrl = categoriaIconoUrl; }
}