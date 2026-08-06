package com.bodega.control.aplicacion.casosuso.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void normalizaRelacionesConIdNulo_antesDeGuardar() {
        Registro registro = new Registro();
        registro.setLote(new Lote());
        registro.setTipo(new Tipo());
        registro.setUbicacion(new Ubicacion());
        when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.guardar(registro);

        ArgumentCaptor<Registro> captor = ArgumentCaptor.forClass(Registro.class);
        verify(repositorio).guardar(captor.capture());
        assertThat(captor.getValue().getLote()).isNull();
        assertThat(captor.getValue().getTipo()).isNull();
        assertThat(captor.getValue().getUbicacion()).isNull();
    }

    @Test
    void conservaRelacionesConIdReal() {
        Lote lote = new Lote();
        lote.setIdLote(5);
        Registro registro = new Registro();
        registro.setLote(lote);
        when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.guardar(registro);

        ArgumentCaptor<Registro> captor = ArgumentCaptor.forClass(Registro.class);
        verify(repositorio).guardar(captor.capture());
        assertThat(captor.getValue().getLote()).isNotNull();
        assertThat(captor.getValue().getLote().getIdLote()).isEqualTo(5);
    }
}
