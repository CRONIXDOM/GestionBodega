package com.bodega.control.aplicacion.casosuso.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.dominio.repositorio.IUsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseImplTest {

    @Mock
    private IUsuarioRepositorio repositorio;

    private UsuarioUseCaseImpl useCase;

    private static Usuario usuario(Integer id, String nombre, String apellido) {
        return new Usuario(id, nombre, apellido, "Activo");
    }

    @BeforeEach
    void setUp() {
        useCase = new UsuarioUseCaseImpl(repositorio);
        lenient().when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));
        lenient().when(repositorio.listarTodos()).thenReturn(List.of(usuario(1, "admin", "Sistema")));
    }

    @Test
    void aceptaNombresConTildesEspaciosYGuiones() {
        useCase.guardar(usuario(null, "José María", "Núñez-Gómez"));

        verify(repositorio).guardar(any());
    }

    @Test
    void rechazaNombreConNumeros() {
        assertThatThrownBy(() -> useCase.guardar(usuario(null, "Juan123", "Perez")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("solo puede contener letras");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void rechazaApellidoConNumeros() {
        assertThatThrownBy(() -> useCase.guardar(usuario(null, "Juan", "Perez7")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("solo puede contener letras");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void rechazaNombreVacio() {
        assertThatThrownBy(() -> useCase.guardar(usuario(null, "   ", "Perez")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("obligatorio");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void rechazaNombreRepetido() {
        assertThatThrownBy(() -> useCase.guardar(usuario(null, "admin", "Otro")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe un usuario");

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void rechazaNombreRepetidoIgnorandoMayusculasYEspacios() {
        assertThatThrownBy(() -> useCase.guardar(usuario(null, "  ADMIN  ", "Otro")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe un usuario");
    }

    @Test
    void permiteGuardarElMismoUsuarioSinCambiarleElNombre() {
        useCase.guardar(usuario(1, "admin", "Sistema Editado"));

        verify(repositorio).guardar(any());
    }

    @Test
    void recortaEspaciosPeroRespetaComoSeEscribio() {
        useCase.guardar(usuario(null, "  Ana  ", "  Lopez  "));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repositorio).guardar(captor.capture());
        assertThat(captor.getValue().getNombreUsuario()).isEqualTo("Ana");
        assertThat(captor.getValue().getApellidoUsuario()).isEqualTo("Lopez");
    }

    @Test
    void juntaLosEspaciosDeEnMedioSinCambiarLasMayusculas() {
        useCase.guardar(usuario(null, "Ana   Maria", "De   la   Cruz"));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repositorio).guardar(captor.capture());
        assertThat(captor.getValue().getNombreUsuario()).isEqualTo("Ana Maria");
        assertThat(captor.getValue().getApellidoUsuario()).isEqualTo("De la Cruz");
    }
}
