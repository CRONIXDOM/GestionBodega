package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.UsuarioRol;

public interface IUsuarioRolUseCase {

    UsuarioRol guardar(UsuarioRol usuarioRol);

    List<UsuarioRol> listarTodos();

    UsuarioRol buscarPorId(int idUsuarioRol);

    void eliminar(int idUsuarioRol);

}
