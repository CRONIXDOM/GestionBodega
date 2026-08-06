package com.bodega.control.aplicacion.casosuso.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.dominio.entidades.Tipo;
import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.dominio.repositorio.IRegistroRepositorio;

@ExtendWith(MockitoExtension.class)
class RegistroUseCaseImplTest {

    @Mock
    private IRegistroRepositorio repositorio;

    private RegistroUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegistroUseCaseImpl(repositorio);
    }

    private static Lote lote(int id) {
        Lote lote = new Lote();
        lote.setIdLote(id);
        return lote;
    }

    private static Tipo tipo(int id) {
        Tipo tipo = new Tipo();
        tipo.setIdTipo(id);
        return tipo;
    }

    @Test
    void rechazaElRegistroCuandoLasRelacionesLleganVacias() {
        Registro registro = new Registro();
        registro.setFechaRegistro(LocalDate.now());
        registro.setLote(new Lote());
        registro.setTipo(new Tipo());
        registro.setUbicacion(new Ubicacion());

        assertThatThrownBy(() -> useCase.guardar(registro))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("obligatorio");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void rechazaElRegistroSinFecha() {
        Registro registro = new Registro();
        registro.setLote(lote(5));
        registro.setTipo(tipo(1));

        assertThatThrownBy(() -> useCase.guardar(registro))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("fecha del registro");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void conservaRelacionesConIdReal() {
        Registro registro = new Registro();
        registro.setFechaRegistro(LocalDate.now());
        registro.setLote(lote(5));
        registro.setTipo(tipo(1));
        // esta llega vacia desde el formulario: debe quedar en null, no en un objeto hueco
        registro.setUbicacion(new Ubicacion());
        when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.guardar(registro);

        ArgumentCaptor<Registro> captor = ArgumentCaptor.forClass(Registro.class);
        verify(repositorio).guardar(captor.capture());
        assertThat(captor.getValue().getLote().getIdLote()).isEqualTo(5);
        assertThat(captor.getValue().getUbicacion()).isNull();
    }
}
