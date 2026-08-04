package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Usuario;

public interface IUsuarioUseCase {
	
	Usuario guardar (Usuario nuevaUsuario);
	
	Usuario buscarPorid (int Usuario);
	
	List<Usuario> listarTodo();
	
	void eliminar (int Usuario);

	Usuario buscarPorId(int idUsuario);

	List<Usuario> listarTodos();
	

}
