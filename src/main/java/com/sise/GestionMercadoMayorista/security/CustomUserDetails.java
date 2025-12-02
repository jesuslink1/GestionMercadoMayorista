package com.sise.GestionMercadoMayorista.security;

import com.sise.GestionMercadoMayorista.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario,
                             Collection<? extends GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // OJO: ya NO tocamos usuario.getRol() aquí
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // puedes refinarlo luego
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // idem
    }

    public String getRolNombre() {
        if (authorities == null || authorities.isEmpty()) {
            return "SIN_ROL";
        }

        String fullRole = authorities.iterator().next().getAuthority();
        return fullRole.replace("ROLE_", "");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // idem
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVO".equalsIgnoreCase(usuario.getEstado());
    }



    public Usuario getUsuario() {
        return usuario;
    }
}
