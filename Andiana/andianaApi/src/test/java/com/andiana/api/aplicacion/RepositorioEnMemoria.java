package com.andiana.api.aplicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Un almacen de datos falso, en un HashMap. Sirve para probar los casos de uso
 * sin levantar Spring ni PostgreSQL: justamente eso es lo que permite tener el
 * dominio separado del framework.
 */
public abstract class RepositorioEnMemoria<T> {

	private final Map<Integer, T> filas = new LinkedHashMap<>();
	private final Function<T, Integer> leerId;
	private final BiConsumer<T, Integer> escribirId;
	private int siguienteId = 1;

	protected RepositorioEnMemoria(Function<T, Integer> leerId, BiConsumer<T, Integer> escribirId) {
		this.leerId = leerId;
		this.escribirId = escribirId;
	}

	public List<T> listarTodos() {
		return new ArrayList<>(filas.values());
	}

	public Optional<T> buscarPorid(int id) {
		return Optional.ofNullable(filas.get(id));
	}

	public T guardar(T entidad) {
		if (leerId.apply(entidad) == null) {
			escribirId.accept(entidad, siguienteId++);
		}
		filas.put(leerId.apply(entidad), entidad);
		return entidad;
	}

	public void eliminar(int id) {
		filas.remove(id);
	}
}
