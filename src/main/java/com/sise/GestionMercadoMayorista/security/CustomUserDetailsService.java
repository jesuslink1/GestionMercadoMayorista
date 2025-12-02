package com.sise.GestionMercadoMayorista.security;

import com.sise.GestionMercadoMayorista.entity.Usuario;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con email: " + email
                ));

        // Aquí todavía estamos dentro de la sesión de Hibernate, así que
        // es seguro acceder a usuario.getRol().getNombreRol()
        String rolNombre = (usuario.getRol() != null && usuario.getRol().getNombreRol() != null)
                ? usuario.getRol().getNombreRol()          // ADMIN, SOCIO, etc.
                : "SIN_ROL";

        String authority = "ROLE_" + rolNombre;            // ROLE_ADMIN, ROLE_SOCIO, etc.
        Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(authority));

        // Pasamos el usuario y las authorities YA calculadas
        return new CustomUserDetails(usuario, authorities);
    }
}