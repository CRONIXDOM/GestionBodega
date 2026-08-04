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