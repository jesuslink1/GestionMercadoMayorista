package com.sise.GestionMercadoMayorista.dto.auth;

public class AuthResponse {

    private String token;
    private String tipoToken = "Bearer";
    private String email;
    private String rol;

    public AuthResponse() {
    }

    public AuthResponse(String token, String email, String rol) {
        this.token = token;
        this.email = email;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}