package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.credencial.CrearCredencialRequest;
import com.sise.GestionMercadoMayorista.dto.credencial.CredencialResponse;
import com.sise.GestionMercadoMayorista.dto.credencial.ValidacionQrResponse;

import java.util.List;

public interface CredencialQrService {

    CredencialResponse crearCredencial(CrearCredencialRequest request);

    CredencialResponse obtenerMiCredencialPorEmail(String emailUsuario);

    ValidacionQrResponse validarPorCodigo(String codigoQr);

    List<CredencialResponse> listarPorUsuario(Integer idUsuario);
}
