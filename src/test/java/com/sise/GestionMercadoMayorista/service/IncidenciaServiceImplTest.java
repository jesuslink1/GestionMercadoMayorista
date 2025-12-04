package com.sise.GestionMercadoMayorista.service;

import com.sise.GestionMercadoMayorista.dto.incidencia.IncidenciaResponseDto;
import com.sise.GestionMercadoMayorista.entity.Incidencia;
import com.sise.GestionMercadoMayorista.exception.ReglaNegocioException;
import com.sise.GestionMercadoMayorista.exception.RecursoNoEncontradoException;
import com.sise.GestionMercadoMayorista.repository.IncidenciaRepository;
import com.sise.GestionMercadoMayorista.repository.StandRepository;
import com.sise.GestionMercadoMayorista.repository.UsuarioRepository;
import com.sise.GestionMercadoMayorista.service.impl.IncidenciaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenciaServiceImplTest {

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @Mock
    private StandRepository standRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private IncidenciaServiceImpl incidenciaService;

    @Test
    void cambiarEstado_CuandoIncidenciaNoExiste_LanzaRecursoNoEncontrado() {
        when(incidenciaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () ->
                incidenciaService.cambiarEstado(1, "EN_PROCESO")
        );
    }

    @Test
    void cambiarEstado_CuandoEstadoNuevoVacio_LanzaReglaNegocio() {
        Incidencia i = new Incidencia();
        i.setId(1);
        i.setEstado("ABIERTA");

        when(incidenciaRepository.findById(1)).thenReturn(Optional.of(i));

        assertThrows(ReglaNegocioException.class, () ->
                incidenciaService.cambiarEstado(1, " ")
        );
    }

    @Test
    void cambiarEstado_CuandoIncidenciaCerrada_NoPermiteReabrir() {
        Incidencia i = new Incidencia();
        i.setId(1);
        i.setEstado("CERRADA");

        when(incidenciaRepository.findById(1)).thenReturn(Optional.of(i));

        assertThrows(ReglaNegocioException.class, () ->
                incidenciaService.cambiarEstado(1, "EN_PROCESO")
        );
    }

    @Test
    void cambiarEstado_TransicionValida_ActualizaEstado() {
        Incidencia i = new Incidencia();
        i.setId(1);
        i.setEstado("ABIERTA");

        when(incidenciaRepository.findById(1)).thenReturn(Optional.of(i));
        when(incidenciaRepository.save(any(Incidencia.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        IncidenciaResponseDto dto = incidenciaService.cambiarEstado(1, "EN_PROCESO");

        assertEquals("EN_PROCESO", dto.getEstado());
    }
}