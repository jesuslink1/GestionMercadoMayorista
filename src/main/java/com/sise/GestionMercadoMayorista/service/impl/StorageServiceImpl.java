package com.sise.GestionMercadoMayorista.service.impl;

import com.sise.GestionMercadoMayorista.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    // TIPOS usados (según lo que me indicaste)
    private static final Set<String> ALLOWED_TIPOS = Set.of("usuarios", "productos", "incidencias");

    // tamaño máximo del lado mayor (estándar recomendado)
    private static final Map<String, Integer> MAX_SIDE = Map.of(
            "usuarios", 512,
            "productos", 1200,
            "incidencias", 1600
    );

    @Value("${app.storage.base-path:./uploads}")
    private String basePath;

    @Override
    public String store(String tipo, MultipartFile file) {
        validarTipo(tipo);
        validarArchivo(file);

        // 1) Validación MIME rápida
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("El archivo debe ser una imagen.");
        }

        // 2) Leer imagen real (si no se puede leer, no es imagen válida)
        BufferedImage original;
        try (InputStream in = file.getInputStream()) {
            original = ImageIO.read(in);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo leer la imagen. Formato no soportado o archivo corrupto.");
        }
        if (original == null) {
            throw new IllegalArgumentException("Imagen inválida o formato no soportado.");
        }

        // 3) Resize manteniendo proporción
        int maxSide = MAX_SIDE.getOrDefault(tipo, 1200);
        BufferedImage resized = resizeKeepingAspect(original, maxSide);

        // 4) Guardar SIEMPRE como WEBP (más liviano)
        String nombreFinal = UUID.randomUUID().toString().replace("-", "") + ".webp";

        Path carpetaDestino = Paths.get(basePath, tipo).toAbsolutePath().normalize();
        Path archivoDestino = carpetaDestino.resolve(nombreFinal).normalize();

        // Seguridad contra path traversal
        if (!archivoDestino.startsWith(carpetaDestino)) {
            throw new IllegalArgumentException("Ruta de archivo inválida.");
        }

        try {
            Files.createDirectories(carpetaDestino);

            boolean ok = ImageIO.write(resized, "webp", archivoDestino.toFile());
            if (!ok) {
                // Esto pasa si no está el plugin webp en Maven
                throw new IllegalStateException("No se pudo generar WEBP. Verifica la dependencia WebP en Maven.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage(), e);
        }

        // 5) URL pública que se guarda en BD
        return "/media/" + tipo + "/" + nombreFinal;
    }

    // ======================
    // Validaciones internas
    // ======================

    private void validarTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo es obligatorio.");
        }
        if (!ALLOWED_TIPOS.contains(tipo)) {
            throw new IllegalArgumentException("Tipo no permitido. Use: " + ALLOWED_TIPOS);
        }
    }

    private void validarArchivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Debe enviar un archivo.");
        }
        if (file.getSize() > 10L * 1024 * 1024) {
            throw new IllegalArgumentException("El archivo supera los 10MB.");
        }
    }

    private BufferedImage resizeKeepingAspect(BufferedImage src, int maxSide) {
        int w = src.getWidth();
        int h = src.getHeight();

        int largest = Math.max(w, h);
        if (largest <= maxSide) return src;

        double scale = (double) maxSide / (double) largest;
        int newW = (int) Math.round(w * scale);
        int newH = (int) Math.round(h * scale);

        // Si tiene transparencia, mantenla
        int type = (src.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;

        BufferedImage out = new BufferedImage(newW, newH, type);
        Graphics2D g = out.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(src, 0, 0, newW, newH, null);
        g.dispose();

        return out;
    }
}