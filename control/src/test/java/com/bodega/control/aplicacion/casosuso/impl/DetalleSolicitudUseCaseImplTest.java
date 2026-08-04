package com.bodega.control.aplicacion.casosuso.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudLoteRepositorio;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudRepositorio;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;

/**
 * Cubre la logica de reserva FIFO: es la parte mas critica del sistema (si se
 * rompe, se puede sobre-reservar stock que no existe) y la que motivo el
 * @Transactional del caso de uso real.
 */
@ExtendWith(MockitoExtension.class)
class DetalleSolicitudUseCaseImplTest {

    @Mock
    private IDetalleSolicitudRepositorio repositorio;
    @Mock
    private ILoteRepositorio loteRepositorio;
    @Mock
    private IDetalleSolicitudLoteRepositorio asignacionRepositorio;

    private DetalleSolicitudUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DetalleSolicitudUseCaseImpl(repositorio, loteRepositorio, asignacionRepositorio);
    }

    private Lote lote(int id, String numero, int cantidad, int reservada, LocalDate ingreso) {
        Lote lote = new Lote();
        lote.setIdLote(id);
        lote.setNumeroLote(numero);
        lote.setCantidadLote(cantidad);
        lote.setCantidadReservada(reservada);
        lote.setFechaIngreso(ingreso);
        return lote;
    }

    private DetalleSolicitud pedido(int idProducto, int cantidad) {
        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        DetalleSolicitud detalle = new DetalleSolicitud();
        detalle.setProducto(producto);
        detalle.setCantidadProducto(cantidad);
        return detalle;
    }

    @Test
    void reservaTodoDeUnSoloLote_cuandoAlcanzaElStock() {
        Lote lote1 = lote(1, "L-001", 50, 0, LocalDate.of(2026, 1, 1));
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(10)).thenReturn(List.of(lote1));
        when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        DetalleSolicitud resultado = useCase.guardar(pedido(10, 20), null);

        assertThat(resultado).isNotNull();
        assertThat(lote1.getCantidadReservada()).isEqualTo(20);
        verify(loteRepositorio, times(1)).guardar(lote1);
        verify(asignacionRepositorio, times(1)).guardar(any());
    }

    @Test
    void reparteEntreVariosLotesEnOrdenFifo_cuandoElPrimeroNoAlcanza() {
        Lote masAntiguo = lote(1, "L-001", 15, 0, LocalDate.of(2026, 1, 1));
        Lote masNuevo = lote(2, "L-002", 100, 0, LocalDate.of(2026, 2, 1));
        // el repositorio ya debe entregarlos en orden FIFO (mas antiguo primero)
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(10)).thenReturn(List.of(masAntiguo, masNuevo));
        when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.guardar(pedido(10, 20), null);

        assertThat(masAntiguo.getCantidadReservada()).isEqualTo(15);
        assertThat(masNuevo.getCantidadReservada()).isEqualTo(5);
        verify(loteRepositorio).guardar(masAntiguo);
        verify(loteRepositorio).guardar(masNuevo);
        verify(asignacionRepositorio, times(2)).guardar(any());
    }

    @Test
    void noReservaNada_cuandoElStockTotalNoAlcanza() {
        Lote unico = lote(1, "L-001", 15, 0, LocalDate.of(2026, 1, 1));
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(10)).thenReturn(List.of(unico));

        assertThatThrownBy(() -> useCase.guardar(pedido(10, 20), null))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Stock insuficiente");

        // nada debe quedar persistido si no alcanza: sin esto, una solicitud que
        // falla a la mitad dejaria reservas "fantasma" sobre stock que no existe.
        verify(loteRepositorio, never()).guardar(any());
        verify(repositorio, never()).guardar(any());
        verify(asignacionRepositorio, never()).guardar(any());
    }

    @Test
    void usaSoloElLoteIndicado_cuandoSeFuerzaUnLoteManual() {
        Lote elegido = lote(7, "L-007", 30, 0, LocalDate.of(2026, 3, 1));
        when(loteRepositorio.buscarPorid(7)).thenReturn(Optional.of(elegido));
        when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.guardar(pedido(10, 10), 7);

        assertThat(elegido.getCantidadReservada()).isEqualTo(10);
        verify(loteRepositorio, never()).buscarPorProductoOrdenadoFifo(anyInt());
    }

    @Test
    void noCuentaLotesSinDisponibilidad() {
        Lote agotado = lote(1, "L-001", 10, 10, LocalDate.of(2026, 1, 1)); // 0 disponible
        Lote conStock = lote(2, "L-002", 20, 0, LocalDate.of(2026, 2, 1));
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(10)).thenReturn(List.of(agotado, conStock));
        when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.guardar(pedido(10, 5), null);

        assertThat(agotado.getCantidadReservada()).isEqualTo(10);
        assertThat(conStock.getCantidadReservada()).isEqualTo(5);
        verify(loteRepositorio, never()).guardar(agotado);
        verify(loteRepositorio).guardar(conStock);
    }
}
