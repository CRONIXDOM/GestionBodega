package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ILoteUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;

public class LoteUseCaseImpl implements ILoteUseCase {

    private final ILoteRepositorio repositorio;

    public LoteUseCaseImpl(ILoteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Lote guardar(Lote nuevoLote) {
		nuevoLote.setNumeroLote(Validaciones.normalizar(nuevoLote.getNumeroLote()));
		Validaciones.obligatorio(nuevoLote.getNumeroLote(), "número de lote");

        if (nuevoLote.getIdLote() != null) {
            repositorio.buscarPorid(nuevoLote.getIdLote())
                    .ifPresent(actual -> nuevoLote.setCantidadReservada(actual.getCantidadReservada()));
        }
        if (nuevoLote.getCantidadReservada() == null) {
            nuevoLote.setCantidadReservada(0);
        }
        if (nuevoLote.getUbicacion() != null && nuevoLote.getUbicacion().getIdUbicacion() == null) {
            nuevoLote.setUbicacion(null);
        }

        Validaciones.obligatorioRelacion(nuevoLote.getProducto(), Producto::getIdProducto, "producto");
        Validaciones.obligatorioRelacion(nuevoLote.getUbicacion(), Ubicacion::getIdUbicacion, "ubicación");
        Validaciones.obligatorioValor(nuevoLote.getFechaIngreso(), "fecha de ingreso");
        Validaciones.obligatorioPositivo(nuevoLote.getCantidadLote(), "cantidad del lote");

        if (nuevoLote.getFechaVencimiento() != null
                && nuevoLote.getFechaVencimiento().isBefore(nuevoLote.getFechaIngreso())) {
            throw new RuntimeException("La fecha de vencimiento no puede ser anterior a la de ingreso");
        }
        // al editar no se puede dejar el lote con menos unidades de las que ya
        // estan comprometidas en reservas pendientes de entregar.
        if (nuevoLote.getCantidadLote() < nuevoLote.getCantidadReservada()) {
            throw new RuntimeException("El lote tiene " + nuevoLote.getCantidadReservada()
                    + " unidades reservadas: no puede quedar con menos de esa cantidad");
        }

        Validaciones.noRepetido(repositorio.listarTodos(), Lote::getIdLote, Lote::getNumeroLote,
                nuevoLote.getIdLote(), nuevoLote.getNumeroLote(), "un lote con el número");

        return repositorio.guardar(nuevoLote);
    }

    @Override
    public Lote buscarPorId(int idLote) {
        return repositorio.buscarPorid(idLote)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
    }

    @Override
    public List<Lote> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idLote) {
        repositorio.eliminar(idLote);
    }

	@Override
	public Lote buscarPorid(int Lote) {
		return repositorio.buscarPorid(Lote)
				.orElseThrow(() -> new RuntimeException("Lote no encontrado"));
	}

}