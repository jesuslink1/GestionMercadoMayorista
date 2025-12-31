package com.sise.GestionMercadoMayorista.config;

import com.sise.GestionMercadoMayorista.security.CustomAccessDeniedHandler;
import com.sise.GestionMercadoMayorista.security.CustomAuthenticationEntryPoint;
import com.sise.GestionMercadoMayorista.security.CustomUserDetailsService;
import com.sise.GestionMercadoMayorista.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService userDetailsService,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLICOS
                        // =========================
                        .requestMatchers(
                                "/api/v1/ping",
                                "/api/auth/login",
                                "/api/public/**",
                                "/error"
                        ).permitAll()

                        // Swagger / OpenAPI
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger.yaml"
                        ).permitAll()

                        // Archivos estáticos (imágenes servidas desde /uploads)
                        .requestMatchers("/media/**").permitAll()

                        // =========================
                        // UPLOAD DE ARCHIVOS
                        // =========================

                        .requestMatchers("/api/v1/files/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "SOCIO")

                        // =========================
                        // ZONAS POR ROL
                        // =========================

                        // Zona ADMIN
                        .requestMatchers("/api/v1/admin/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR")

                        // Stands
                        .requestMatchers("/api/v1/stands/mis-stands/**")
                        .hasRole("SOCIO")
                        .requestMatchers("/api/v1/stands/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "SOCIO")

                        // Zona SOCIO (incluye credencial QR)
                        .requestMatchers("/api/v1/socio/**")
                        .hasRole("SOCIO")

                        // Validación de QR (seguridad/admin/supervisor)
                        .requestMatchers("/api/v1/credenciales/validar/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR")

                        // Zona CLIENTE
                        .requestMatchers("/api/v1/cliente/**")
                        .hasRole("CLIENTE")

                        // Resto autenticado
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:3000"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        // IMPORTANTE: para multipart también está bien Content-Type
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}