package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.UsuarioRol;

public interface IUsuarioRolRepositorio {
	
	UsuarioRol guardar (UsuarioRol nuevaUsuario);
	
	Optional<UsuarioRol> buscarPorId(int idUsuarioRol);
	
	List<UsuarioRol> listarTodos();
	
	void eliminar (int UsuarioRol);


}
