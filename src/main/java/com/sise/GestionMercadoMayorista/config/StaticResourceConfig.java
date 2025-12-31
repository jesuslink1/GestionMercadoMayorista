package com.sise.GestionMercadoMayorista.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${app.storage.base-path:./uploads}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadsDir = Paths.get(basePath).toAbsolutePath().normalize();
        String location = uploadsDir.toUri().toString(); // file:///C:/.../uploads/

        // Asegura que termine con /
        if (!StringUtils.endsWithIgnoreCase(location, "/")) {
            location = location + "/";
        }

        registry.addResourceHandler("/media/**")
                .addResourceLocations(location)
                .setCachePeriod(3600); // 1h cache (opcional)
    }
}
