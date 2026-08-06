package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;
import java.util.function.Function;

import org.springframework.transaction.annotation.Transactional;

import com.bodega.control.aplicacion.casosuso.entrada.IDetalleEntregaUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.dominio.entidades.DetalleSolicitudLote;
import com.bodega.control.dominio.entidades.Entrega;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.repositorio.IDetalleEntregaRepositorio;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudLoteRepositorio;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;

public class DetalleEntregaUseCaseImpl implements IDetalleEntregaUseCase {

    private final IDetalleEntregaRepositorio repositorio;
    private final ILoteRepositorio loteRepositorio;
    private final IDetalleSolicitudLoteRepositorio asignacionRepositorio;

    public DetalleEntregaUseCaseImpl(IDetalleEntregaRepositorio repositorio, ILoteRepositorio loteRepositorio,
            IDetalleSolicitudLoteRepositorio asignacionRepositorio) {
        this.repositorio = repositorio;
        this.loteRepositorio = loteRepositorio;
        this.asignacionRepositorio = asignacionRepositorio;
    }

    @Override
    @Transactional
    public DetalleEntrega guardar(DetalleEntrega nuevoDetalleEntrega) {
        Integer idLoteManual = normalizarId(nuevoDetalleEntrega.getLote(), Lote::getIdLote);
        if (idLoteManual == null) {
            nuevoDetalleEntrega.setLote(null);
        }
        Integer idDetalleSolicitud = normalizarId(nuevoDetalleEntrega.getDetalleSolicitud(),
                DetalleSolicitud::getIdDetalleSolicitud);
        if (idDetalleSolicitud == null) {
            nuevoDetalleEntrega.setDetalleSolicitud(null);
        }
        if (normalizarId(nuevoDetalleEntrega.getProducto(), Producto::getIdProducto) == null) {
            nuevoDetalleEntrega.setProducto(null);
        }
        if (normalizarId(nuevoDetalleEntrega.getEntrega(), Entrega::getIdEntrega) == null) {
            nuevoDetalleEntrega.setEntrega(null);
        }

        nuevoDetalleEntrega.setCodigoEvento(Validaciones.normalizar(nuevoDetalleEntrega.getCodigoEvento()));
        nuevoDetalleEntrega.setNombreEvento(Validaciones.normalizar(nuevoDetalleEntrega.getNombreEvento()));

        if (idDetalleSolicitud != null) {
            despacharDesdeReserva(idDetalleSolicitud);
        } else if (nuevoDetalleEntrega.getProducto() != null) {
            Integer cantidad = nuevoDetalleEntrega.getCantidadProducto();
            if (cantidad == null || cantidad <= 0) {
                throw new RuntimeException("La cantidad a despachar debe ser mayor que cero");
            }
            despacharFifo(nuevoDetalleEntrega.getProducto().getIdProducto(), cantidad, idLoteManual);
        } else {
            // sin pedido y sin producto no hay nada que descontar: guardarlo dejaria
            // una entrega fantasma que no mueve stock.
            throw new RuntimeException(
                    "Indica el pedido que se está entregando, o bien el producto y la cantidad a despachar");
        }
        return repositorio.guardar(nuevoDetalleEntrega);
    }

    private static <T> Integer normalizarId(T objeto, Function<T, Integer> getId) {
        return objeto == null ? null : getId.apply(objeto);
    }

    private void despacharDesdeReserva(int idDetalleSolicitud) {
        List<DetalleSolicitudLote> asignaciones = asignacionRepositorio.buscarPorDetalleSolicitud(idDetalleSolicitud);
        if (asignaciones.isEmpty()) {
            throw new RuntimeException("El Detalle Solicitud indicado no tiene stock reservado");
        }
        // se comprueba primero que TODOS los lotes tengan la cantidad, antes de
        // tocar ninguno: asi no queda un descuento a medias si uno de ellos falla.
        for (DetalleSolicitudLote asignacion : asignaciones) {
            Lote lote = loteRepositorio.buscarPorid(asignacion.getLote().getIdLote())
                    .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
            int enBodega = lote.getCantidadLote() == null ? 0 : lote.getCantidadLote();
            if (enBodega < asignacion.getCantidad()) {
                throw new RuntimeException("No se puede despachar: el lote " + lote.getNumeroLote() + " tiene "
                        + enBodega + " unidades y el pedido requiere " + asignacion.getCantidad());
            }
        }
        for (DetalleSolicitudLote asignacion : asignaciones) {
            Lote lote = loteRepositorio.buscarPorid(asignacion.getLote().getIdLote())
                    .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
            lote.setCantidadLote(lote.getCantidadLote() - asignacion.getCantidad());
            lote.setCantidadReservada(lote.getCantidadReservada() - asignacion.getCantidad());
            loteRepositorio.guardar(lote);
        }
    }

    private void despacharFifo(int idProducto, int cantidadPedida, Integer idLoteManual) {
        List<Lote> candidatos;
        if (idLoteManual != null) {
            candidatos = List.of(loteRepositorio.buscarPorid(idLoteManual)
                    .orElseThrow(() -> new RuntimeException("Lote no encontrado")));
        } else {
            candidatos = loteRepositorio.buscarPorProductoOrdenadoFifo(idProducto);
        }

        int cantidadRestante = cantidadPedida;
        for (Lote lote : candidatos) {
            if (cantidadRestante <= 0) {
                break;
            }
            int disponible = lote.getCantidadDisponible();
            if (disponible <= 0) {
                continue;
            }
            int aTomar = Math.min(disponible, cantidadRestante);
            lote.setCantidadLote(lote.getCantidadLote() - aTomar);
            loteRepositorio.guardar(lote);
            cantidadRestante -= aTomar;
        }

        if (cantidadRestante > 0) {
            throw new RuntimeException("Stock insuficiente: solo hay " + (cantidadPedida - cantidadRestante)
                    + " unidades disponibles del producto solicitado");
        }
    }

    @Override
    public DetalleEntrega buscarPorid(int idDetalleEntrega) {
        return repositorio.buscarPorid(idDetalleEntrega)
                .orElseThrow(() -> new RuntimeException("Detalle de entrega no encontrado"));
    }

    @Override
    public List<DetalleEntrega> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idDetalleEntrega) {
        repositorio.eliminar(idDetalleEntrega);
    }

}
