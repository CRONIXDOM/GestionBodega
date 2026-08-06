package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IRolUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Rol;
import com.bodega.control.dominio.repositorio.IRolRepositorio;

public class RolUseCaseImpl implements IRolUseCase {

    private final IRolRepositorio repositorio;

    public RolUseCaseImpl(IRolRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Rol guardar(Rol nuevoRol) {
		nuevoRol.setNombreRol(Validaciones.normalizar(nuevoRol.getNombreRol()));
		nuevoRol.setDescripcionRol(Validaciones.normalizar(nuevoRol.getDescripcionRol()));

        return repositorio.guardar(nuevoRol);
    }

    @Override
    public Rol buscarPorId(int idRol) {
        return repositorio.buscarPorid(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    @Override
    public List<Rol> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idRol) {
        repositorio.eliminar(idRol);
    }

	@Override
	public Rol buscarPorid(int Rol) {
		return null;
	}

}