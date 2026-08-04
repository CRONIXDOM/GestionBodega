package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioRolUseCase;
import com.bodega.control.dominio.entidades.UsuarioRol;
import com.bodega.control.dominio.repositorio.IUsuarioRolRepositorio;

public class UsuarioRolUseCaseImpl implements IUsuarioRolUseCase {

    private final IUsuarioRolRepositorio repositorio;

    public UsuarioRolUseCaseImpl(IUsuarioRolRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public UsuarioRol guardar(UsuarioRol nuevoUsuarioRol) {
        return repositorio.guardar(nuevoUsuarioRol);
    }

    @Override
	public UsuarioRol buscarPorId(int idUsuarioRol) {
	    return repositorio.buscarPorId(idUsuarioRol)
	          .orElseThrow(() -> new RuntimeException("UsuarioRol no encontrado"));
	}

    @Override
    public List<UsuarioRol> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idUsuarioRol) {
        repositorio.eliminar(idUsuarioRol);
    }

}