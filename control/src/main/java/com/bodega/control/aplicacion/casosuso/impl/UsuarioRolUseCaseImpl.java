package com.bodega.control.aplicacion.casosuso.impl;

import java.time.LocalDate;
import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioRolUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Rol;
import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.dominio.entidades.UsuarioRol;
import com.bodega.control.dominio.repositorio.IUsuarioRolRepositorio;

public class UsuarioRolUseCaseImpl implements IUsuarioRolUseCase {

    private final IUsuarioRolRepositorio repositorio;

    public UsuarioRolUseCaseImpl(IUsuarioRolRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public UsuarioRol guardar(UsuarioRol nuevoUsuarioRol) {
        Validaciones.obligatorioRelacion(nuevoUsuarioRol.getUsuario(), Usuario::getIdUsuario, "usuario");
        Validaciones.obligatorioRelacion(nuevoUsuarioRol.getRol(), Rol::getIdRol, "rol");
        if (nuevoUsuarioRol.getFechaAsignacion() == null) {
            nuevoUsuarioRol.setFechaAsignacion(LocalDate.now());
        }

        // asignar dos veces el mismo rol al mismo usuario no aporta nada y duplica
        // las filas que luego se listan en solicitudes y registros.
        Integer idUsuario = nuevoUsuarioRol.getUsuario().getIdUsuario();
        Integer idRol = nuevoUsuarioRol.getRol().getIdRol();
        boolean repetido = repositorio.listarTodos().stream()
                .filter(otro -> nuevoUsuarioRol.getIdUsuarioRol() == null
                        || !nuevoUsuarioRol.getIdUsuarioRol().equals(otro.getIdUsuarioRol()))
                .anyMatch(otro -> otro.getUsuario() != null && otro.getRol() != null
                        && idUsuario.equals(otro.getUsuario().getIdUsuario())
                        && idRol.equals(otro.getRol().getIdRol()));
        if (repetido) {
            throw new RuntimeException("Ese usuario ya tiene asignado ese rol");
        }

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