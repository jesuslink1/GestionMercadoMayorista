package com.sise.GestionMercadoMayorista.job;

import com.sise.GestionMercadoMayorista.repository.CuotaPagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class CuotasVencidasScheduler {

    private static final Logger log = LoggerFactory.getLogger(CuotasVencidasScheduler.class);

    private final CuotaPagoRepository cuotaPagoRepository;

    public CuotasVencidasScheduler(CuotaPagoRepository cuotaPagoRepository) {
        this.cuotaPagoRepository = cuotaPagoRepository;
    }

    /**
     * Job diario que marca como VENCIDO las cuotas que:
     * - Están en estado PENDIENTE o PARCIAL
     * - Tienen fechaVencimiento < hoy
     * - Y estadoRegistro = 1
     *
     * Cron: "0 0 2 * * *"  → todos los días a las 02:00 am.
     */
    @Transactional
    @Scheduled(cron = "0 0 2 * * *")
    public void marcarCuotasVencidas() {
        LocalDate hoy = LocalDate.now();
        int actualizadas = cuotaPagoRepository.marcarVencidas(hoy);

        if (actualizadas > 0) {
            log.info("Job CuotasVencidasScheduler: {} cuotas marcadas como VENCIDO (fecha < {}).",
                    actualizadas, hoy);
        } else {
            log.info("Job CuotasVencidasScheduler: no había cuotas por vencer (fecha < {}).", hoy);
        }
    }
}
