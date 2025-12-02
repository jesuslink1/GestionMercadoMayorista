package com.sise.GestionMercadoMayorista.repository;

import com.sise.GestionMercadoMayorista.entity.CredencialQr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredencialQrRepository extends JpaRepository<CredencialQr, Integer> {

    List<CredencialQr> findByUsuarioIdAndVigenteTrue(Integer idUsuario);
}
