package com.bodega.control.aplicacion.casosuso.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bodega.control.dominio.entidades.Sede;
import com.bodega.control.dominio.repositorio.ISedeRepositorio;

/**
 * Reglas al registrar una sede: nombre y dirección no pueden repetirse, la
 * capacidad y la descripción son obligatorias, y el texto se guarda siempre en
 * mayúsculas sin importar cómo lo escriba el usuario.
 */
@ExtendWith(MockitoExtension.class)
class SedeUseCaseImplTest {

    @Mock
    private ISedeRepositorio repositorio;

    private SedeUseCaseImpl useCase;

    private static Sede sede(Integer id, String nombre, String direccion) {
        return new Sede(id, nombre, direccion, "BODEGA", 100);
    }

    @BeforeEach
    void setUp() {
        useCase = new SedeUseCaseImpl(repositorio);
        lenient().when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));
        lenient().when(repositorio.listarTodos())
                .thenReturn(List.of(sede(1, "SEDE NORTE", "AV. PRINCIPAL 123")));
    }

    @Test
    void guardaElTextoEnMayusculas() {
        useCase.guardar(new Sede(null, "  sede sur  ", "calle nueve", "bodega secundaria", 50));

        ArgumentCaptor<Sede> captor = ArgumentCaptor.forClass(Sede.class);
        verify(repositorio).guardar(captor.capture());
        assertThat(captor.getValue().getNombreSede()).isEqualTo("SEDE SUR");
        assertThat(captor.getValue().getDireccion()).isEqualTo("CALLE NUEVE");
        assertThat(captor.getValue().getDescripcion()).isEqualTo("BODEGA SECUNDARIA");
    }

    @Test
    void juntaLosEspaciosDeEnMedio() {
        useCase.guardar(new Sede(null, "  bodega   del   sur ", "calle  nueve", "una  bodega", 50));

        ArgumentCaptor<Sede> captor = ArgumentCaptor.forClass(Sede.class);
        verify(repositorio).guardar(captor.capture());
        assertThat(captor.getValue().getNombreSede()).isEqualTo("BODEGA DEL SUR");
        assertThat(captor.getValue().getDireccion()).isEqualTo("CALLE NUEVE");
    }

    @Test
    void rechazaNombreRepetidoConEspaciosDeMas() {
        // en pantalla los dos se ven igual, asi que no pueden convivir
        assertThatThrownBy(() -> useCase.guardar(sede(null, "  sede   norte  ", "OTRA DIRECCION")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe una sede con el nombre");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void rechazaNombreRepetidoAunqueSeEscribaEnMinusculas() {
        assertThatThrownBy(() -> useCase.guardar(sede(null, "sede norte", "OTRA DIRECCION")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe una sede con el nombre");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void rechazaDireccionRepetida() {
        assertThatThrownBy(() -> useCase.guardar(sede(null, "SEDE SUR", "av. principal 123")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe una sede en la dirección");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void exigeCapacidad() {
        assertThatThrownBy(() -> useCase.guardar(new Sede(null, "SEDE SUR", "CALLE 9", "BODEGA", null)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("capacidad es obligatorio");
    }

    @Test
    void exigeCapacidadMayorQueCero() {
        assertThatThrownBy(() -> useCase.guardar(new Sede(null, "SEDE SUR", "CALLE 9", "BODEGA", 0)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("mayor que cero");
    }

    @Test
    void exigeDescripcion() {
        assertThatThrownBy(() -> useCase.guardar(new Sede(null, "SEDE SUR", "CALLE 9", "   ", 10)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("descripción es obligatorio");
    }

    @Test
    void permiteEditarLaMismaSedeSinCambiarleElNombre() {
        useCase.guardar(sede(1, "SEDE NORTE", "AV. PRINCIPAL 123"));

        verify(repositorio).guardar(any());
    }
}
