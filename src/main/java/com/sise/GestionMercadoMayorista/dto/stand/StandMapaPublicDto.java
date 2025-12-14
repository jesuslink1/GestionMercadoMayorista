package com.sise.GestionMercadoMayorista.dto.stand;

public class StandMapaPublicDto {

    private Integer id;

    private String bloque;          // A, B, C, D...
    private String numeroStand;     // A-01, B-05, etc.

    private String nombreComercial; // Si es null, mostramos "Disponible A-01"
    private String rubro;           // nombreCategoriaStand o "---"

    /**
     * Estado pensado para el mapa:
     *  - "OCUPADO"    => se pinta verde
     *  - "DISPONIBLE" => se pinta gris/naranja según la leyenda
     */
    private String estado;

    public StandMapaPublicDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}