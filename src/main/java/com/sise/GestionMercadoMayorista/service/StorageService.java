package com.sise.GestionMercadoMayorista.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(String tipo, MultipartFile file);

}
