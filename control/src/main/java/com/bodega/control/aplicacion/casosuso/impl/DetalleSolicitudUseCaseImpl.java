package com.bodega.control.aplicacion.casosuso.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bodega.control.aplicacion.casosuso.entrada.IDetalleSolicitudUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
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

    @Override
    @Transactional
    public DetalleSolicitud guardar(DetalleSolicitud nuevoDetalleSolicitud, Integer idLoteManual) {
        // el mapeador siempre crea el objeto anidado aunque no llegue el id, asi que
        // hay que mirar el id y no solo si el objeto es null.
        if (nuevoDetalleSolicitud.getProducto() == null || nuevoDetalleSolicitud.getProducto().getIdProducto() == null) {
            throw new RuntimeException("Indica el producto que se va a reservar");
        }
        if (nuevoDetalleSolicitud.getSolicitud() == null
                || nuevoDetalleSolicitud.getSolicitud().getIdSolicitud() == null) {
            throw new RuntimeException("Indica la solicitud a la que pertenece la reserva");
        }
        Validaciones.obligatorioPositivo(nuevoDetalleSolicitud.getCantidadProducto(), "cantidad a reservar");
        nuevoDetalleSolicitud.setLugarRecogida(Validaciones.normalizar(nuevoDetalleSolicitud.getLugarRecogida()));

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
