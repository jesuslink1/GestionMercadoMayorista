package com.sise.GestionMercadoMayorista.util;

import java.util.UUID;

public class QrTokenGenerator {

    private QrTokenGenerator() {}

    public static String generarCodigoQr() {

        return UUID.randomUUID().toString();
    }
}
