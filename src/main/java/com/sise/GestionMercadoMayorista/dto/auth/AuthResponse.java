package com.sise.GestionMercadoMayorista.dto.auth;

public class AuthResponse {

    private String token;
    private String tipoToken = "Bearer";
    private String email;
    private String rol;

    // ✅ NUEVO
    private String nombreCompleto;
    private String fotoUrl; // ej: "/media/usuarios/xxx.webp"

    public AuthResponse() {}

    public AuthResponse(String token, String email, String rol) {
        this.token = token;
        this.email = email;
        this.rol = rol;
    }

    public AuthResponse(String token, String email, String rol, String nombreCompleto, String fotoUrl) {
        this.token = token;
        this.email = email;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.fotoUrl = fotoUrl;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipoToken() { return tipoToken; }
    public void setTipoToken(String tipoToken) { this.tipoToken = tipoToken; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}