package com.bodega.control.aplicacion.casosuso.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bodega.control.aplicacion.casosuso.entrada.IDetalleSolicitudUseCase;
import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.dominio.entidades.DetalleSolicitudLote;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudLoteRepositorio;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudRepositorio;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;

public class DetalleSolicitudUseCaseImpl implements IDetalleSolicitudUseCase {

    private final IDetalleSolicitudRepositorio repositorio;
    private final ILoteRepositorio loteRepositorio;
    private final IDetalleSolicitudLoteRepositorio asignacionRepositorio;

    public DetalleSolicitudUseCaseImpl(IDetalleSolicitudRepositorio repositorio, ILoteRepositorio loteRepositorio,
            IDetalleSolicitudLoteRepositorio asignacionRepositorio) {
        this.repositorio = repositorio;
        this.loteRepositorio = loteRepositorio;
        this.asignacionRepositorio = asignacionRepositorio;
    }

    /**
     * Reserva stock (FIFO por fecha de ingreso, salvo que se indique un lote puntual)
     * para cubrir la cantidad pedida, y solo despues guarda el Detalle Solicitud.
     * La reserva se registra sumando a "cantidadReservada" del/los lote(s) usados,
     * sin tocar todavia "cantidadLote" (eso ocurre recien cuando se registre la Entrega).
     */
    // atomico a proposito: si no alcanza el stock, ninguna de las reservas
    // parciales que ya se hayan guardado en el bucle debe quedar en firme.
    @Override
    @Transactional
    public DetalleSolicitud guardar(DetalleSolicitud nuevoDetalleSolicitud, Integer idLoteManual) {
        int idProducto = nuevoDetalleSolicitud.getProducto().getIdProducto();
        int cantidadPedida = nuevoDetalleSolicitud.getCantidadProducto();

        List<Lote> candidatos;
        if (idLoteManual != null) {
            candidatos = List.of(loteRepositorio.buscarPorid(idLoteManual)
                    .orElseThrow(() -> new RuntimeException("Lote no encontrado")));
        } else {
            candidatos = loteRepositorio.buscarPorProductoOrdenadoFifo(idProducto);
        }

        List<Lote> lotesTocados = new ArrayList<>();
        List<Integer> cantidadesTomadas = new ArrayList<>();
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
            lote.setCantidadReservada(lote.getCantidadReservada() + aTomar);
            lotesTocados.add(lote);
            cantidadesTomadas.add(aTomar);
            cantidadRestante -= aTomar;
        }

        if (cantidadRestante > 0) {
            throw new RuntimeException("Stock insuficiente: solo hay " + (cantidadPedida - cantidadRestante)
                    + " unidades disponibles del producto solicitado");
        }

        // recien se persisten los cambios una vez que se confirmo que alcanza el stock
        for (Lote lote : lotesTocados) {
            loteRepositorio.guardar(lote);
        }

        DetalleSolicitud guardado = repositorio.guardar(nuevoDetalleSolicitud);

        for (int i = 0; i < lotesTocados.size(); i++) {
            DetalleSolicitudLote asignacion = new DetalleSolicitudLote(null, guardado, lotesTocados.get(i),
                    cantidadesTomadas.get(i));
            asignacionRepositorio.guardar(asignacion);
        }

        return guardado;
    }

    @Override
    public DetalleSolicitud buscarPorId(int idDetalleSolicitud) {
        return repositorio.buscarPorid(idDetalleSolicitud)
                .orElseThrow(() -> new RuntimeException("Detalle de solicitud no encontrado"));
    }

    @Override
    public List<DetalleSolicitud> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idDetalleSolicitud) {
        repositorio.eliminar(idDetalleSolicitud);
    }

	@Override
	public DetalleSolicitud buscarPorid(int idDetalleSolicitud) {
		return buscarPorId(idDetalleSolicitud);
	}

	@Override
	public List<DetalleSolicitudLote> obtenerAsignaciones(int idDetalleSolicitud) {
		return asignacionRepositorio.buscarPorDetalleSolicitud(idDetalleSolicitud);
	}

}
