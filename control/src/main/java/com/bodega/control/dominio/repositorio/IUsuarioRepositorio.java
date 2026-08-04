package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Usuario;

public interface IUsuarioRepositorio {
	
	Usuario guardar (Usuario nuevaUsuario);
	
	Optional<Usuario> buscarPorid (int Usuario);
	
	List<Usuario> listarTodos(); 
	
	void eliminar (int Usuario);

}
