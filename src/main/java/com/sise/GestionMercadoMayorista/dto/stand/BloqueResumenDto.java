package com.sise.GestionMercadoMayorista.dto.stand;
    public class BloqueResumenDto {

    private String bloque;
    private Long totalStands;

    public BloqueResumenDto() {
    }

    public BloqueResumenDto(String bloque, Long totalStands) {
        this.bloque = bloque;
        this.totalStands = totalStands;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public Long getTotalStands() {
        return totalStands;
    }

    public void setTotalStands(Long totalStands) {
        this.totalStands = totalStands;
    }
}
