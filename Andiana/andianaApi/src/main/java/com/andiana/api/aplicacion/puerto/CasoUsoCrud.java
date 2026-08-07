package com.andiana.api.aplicacion.puerto;

import java.util.List;

/**
 * Lo que la capa de presentacion puede pedirle a la aplicacion sobre cualquier
 * entidad. Los controladores dependen de esta interfaz, no de la clase que la
 * implementa.
 */
public interface CasoUsoCrud<T> {

	List<T> listar();

	T buscarPorId(Integer id);

	T guardar(T entidad);

	void eliminar(Integer id);
}
