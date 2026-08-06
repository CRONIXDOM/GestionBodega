package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ITipoUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Tipo;
import com.bodega.control.dominio.repositorio.ITipoRepositorio;

public class TipoUseCaseImpl implements ITipoUseCase {

    private final ITipoRepositorio repositorio;

    public TipoUseCaseImpl(ITipoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Tipo guardar(Tipo nuevoTipo) {
		nuevoTipo.setDescripcion(Validaciones.normalizar(nuevoTipo.getDescripcion()));
		nuevoTipo.setClase(Validaciones.normalizar(nuevoTipo.getClase()));
		Validaciones.obligatorio(nuevoTipo.getDescripcion(), "descripción del tipo");

        return repositorio.guardar(nuevoTipo);
    }

    @Override
    public Tipo buscarPorId(int idTipo) {
        return repositorio.buscarPorid(idTipo)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
    }

    @Override
    public List<Tipo> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idTipo) {
        repositorio.eliminar(idTipo);
    }

	@Override
	public Tipo buscarPorid(int Tipo) {
		return null;
	}

}