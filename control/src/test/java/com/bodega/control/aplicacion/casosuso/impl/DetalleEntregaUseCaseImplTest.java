package com.bodega.control.aplicacion.casosuso.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.dominio.entidades.DetalleSolicitudLote;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.repositorio.IDetalleEntregaRepositorio;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudLoteRepositorio;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;

/**
 * Cubre las dos formas en que una entrega puede descontar stock: salida
 * directa por FIFO (sin pedido previo) y despacho que cumple exactamente una
 * reserva ya hecha por un Detalle Solicitud.
 */
@ExtendWith(MockitoExtension.class)
class DetalleEntregaUseCaseImplTest {

    @Mock
    private IDetalleEntregaRepositorio repositorio;
    @Mock
    private ILoteRepositorio loteRepositorio;
    @Mock
    private IDetalleSolicitudLoteRepositorio asignacionRepositorio;

    private DetalleEntregaUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DetalleEntregaUseCaseImpl(repositorio, loteRepositorio, asignacionRepositorio);
        // lenient: los casos que esperan excepcion nunca llegan a llamar guardar()
        lenient().when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    private Lote lote(int id, int cantidad, int reservada) {
        Lote lote = new Lote();
        lote.setIdLote(id);
        lote.setCantidadLote(cantidad);
        lote.setCantidadReservada(reservada);
        return lote;
    }

    @Test
    void salidaDirectaPorFifo_descuentaSoloCantidadLote() {
        Lote lote = lote(1, 40, 0);
        Producto producto = new Producto();
        producto.setIdProducto(10);
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(10)).thenReturn(List.of(lote));

        DetalleEntrega entrega = new DetalleEntrega();
        entrega.setProducto(producto);
        entrega.setCantidadProducto(15);

        useCase.guardar(entrega);

        assertThat(lote.getCantidadLote()).isEqualTo(25);
        assertThat(lote.getCantidadReservada()).isZero();
    }

    @Test
    void salidaDirecta_lanzaExcepcion_cuandoNoAlcanzaElStock() {
        Lote lote = lote(1, 5, 0);
        Producto producto = new Producto();
        producto.setIdProducto(10);
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(10)).thenReturn(List.of(lote));

        DetalleEntrega entrega = new DetalleEntrega();
        entrega.setProducto(producto);
        entrega.setCantidadProducto(15);

        assertThatThrownBy(() -> useCase.guardar(entrega))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Stock insuficiente");
    }

    @Test
    void cumplirReserva_descuentaCantidadLoteYCantidadReservada() {
        Lote reservado = lote(1, 40, 12);
        when(loteRepositorio.buscarPorid(1)).thenReturn(Optional.of(reservado));

        DetalleSolicitud solicitud = new DetalleSolicitud();
        solicitud.setIdDetalleSolicitud(99);
        DetalleSolicitudLote asignacion = new DetalleSolicitudLote(1, solicitud, lote(1, 0, 0), 12);
        // el lote referenciado en la asignacion solo necesita el id para buscarlo
        asignacion.getLote().setIdLote(1);
        when(asignacionRepositorio.buscarPorDetalleSolicitud(99)).thenReturn(List.of(asignacion));

        DetalleEntrega entrega = new DetalleEntrega();
        entrega.setDetalleSolicitud(solicitud);

        useCase.guardar(entrega);

        assertThat(reservado.getCantidadLote()).isEqualTo(28);
        assertThat(reservado.getCantidadReservada()).isZero();
    }

    @Test
    void cumplirReserva_lanzaExcepcion_cuandoNoHayNadaReservado() {
        DetalleSolicitud solicitud = new DetalleSolicitud();
        solicitud.setIdDetalleSolicitud(99);
        when(asignacionRepositorio.buscarPorDetalleSolicitud(99)).thenReturn(List.of());

        DetalleEntrega entrega = new DetalleEntrega();
        entrega.setDetalleSolicitud(solicitud);

        assertThatThrownBy(() -> useCase.guardar(entrega))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no tiene stock reservado");
    }
}
