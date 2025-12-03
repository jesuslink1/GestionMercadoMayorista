package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.CredencialQr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredencialQrRepository extends JpaRepository<CredencialQr, Integer> {

    // Para que el socio vea su credencial vigente
    Optional<CredencialQr> findByIdUsuarioAndVigenteTrueAndEstadoRegistro(Integer idUsuario, Integer estadoRegistro);

    // Para validar QR por código
    Optional<CredencialQr> findByCodigoQrAndEstadoRegistro(String codigoQr, Integer estadoRegistro);

    // Opcional: para invalidar la última credencial al generar una nueva
    Optional<CredencialQr> findFirstByIdUsuarioAndEstadoRegistroOrderByFechaEmisionDesc(Integer idUsuario, Integer estadoRegistro);
}
