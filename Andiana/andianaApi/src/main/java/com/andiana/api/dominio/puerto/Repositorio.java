package com.andiana.api.dominio.puerto;

import java.util.List;
import java.util.Optional;

/**
 * Lo que el dominio necesita de cualquier almacen de datos. Es una interfaz
 * propia, sin nada de JPA ni de Spring: quien la implementa vive en la capa de
 * infraestructura, asi que se podria cambiar PostgreSQL por otra cosa sin tocar
 * ni una linea del dominio ni de los casos de uso.
 */
public interface Repositorio<T> {

	List<T> listar();

	Optional<T> buscarPorId(Integer id);

	T guardar(T entidad);

	void eliminar(Integer id);
}
