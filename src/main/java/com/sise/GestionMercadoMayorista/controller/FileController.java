package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.service.StorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Upload genérico
     * POST /api/v1/files/upload/{tipo}
     * form-data:
     *  - file: <archivo>
     * Response:
     *  { "url": "/media/productos/xxxx.png" }
     */
    @PostMapping(
            value = "/upload/{tipo}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, String>> upload(
            @PathVariable String tipo,
            @RequestPart("file") MultipartFile file
    ) {
        String url = storageService.store(tipo, file);
        return ResponseEntity.ok(Map.of("url", url));
    }
}