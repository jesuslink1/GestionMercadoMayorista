package com.sise.GestionMercadoMayorista.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Gestión Mercado Mayorista",
                version = "1.0",
                description = "Backend del proyecto de titulación - Gestión de mercado mayorista (stands, productos, cuotas, incidencias, calificaciones, credenciales QR).",
                contact = @Contact(
                        name = "Jesús Ramos",
                        email = "jesus.ramos@mercado.com"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",              // nombre que usaremos en @SecurityRequirement
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

public class OpenApiConfig {

}
