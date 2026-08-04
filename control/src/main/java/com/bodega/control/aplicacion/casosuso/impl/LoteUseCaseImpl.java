package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ILoteUseCase;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;

public class LoteUseCaseImpl implements ILoteUseCase {

    private final ILoteRepositorio repositorio;

    public LoteUseCaseImpl(ILoteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Lote guardar(Lote nuevoLote) {
        if (nuevoLote.getIdLote() != null) {
            // es una edicion: la cantidad reservada la administra el flujo de FIFO,
            // no el formulario de edicion de lote, asi que se preserva la existente.
            repositorio.buscarPorid(nuevoLote.getIdLote())
                    .ifPresent(actual -> nuevoLote.setCantidadReservada(actual.getCantidadReservada()));
        }
        if (nuevoLote.getCantidadReservada() == null) {
            nuevoLote.setCantidadReservada(0);
        }
        // el mapper crea un objeto Ubicacion "cascaron" (idUbicacion=null) cuando el
        // formulario no elige ubicacion; hay que normalizarlo a null real o Hibernate
        // intenta guardarlo como una ubicacion nueva en vez de tratarlo como ausente.
        if (nuevoLote.getUbicacion() != null && nuevoLote.getUbicacion().getIdUbicacion() == null) {
            nuevoLote.setUbicacion(null);
        }
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
		// TODO Auto-generated method stub
		return null;
	}

}