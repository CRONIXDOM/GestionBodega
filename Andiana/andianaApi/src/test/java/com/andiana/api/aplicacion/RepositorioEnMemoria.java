package com.andiana.api.aplicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.andiana.api.dominio.puerto.Repositorio;

/**
 * Un almacen de datos falso, en un HashMap. Sirve para probar los casos de uso
 * sin levantar Spring ni PostgreSQL: justamente eso es lo que permite tener el
 * dominio separado del framework.
 */
public class RepositorioEnMemoria<T> implements Repositorio<T> {

	private final Map<Integer, T> filas = new LinkedHashMap<>();
	private final Function<T, Integer> leerId;
	private final BiConsumer<T, Integer> escribirId;
	private int siguienteId = 1;

	public RepositorioEnMemoria(Function<T, Integer> leerId, BiConsumer<T, Integer> escribirId) {
		this.leerId = leerId;
		this.escribirId = escribirId;
	}

	@Override
	public List<T> listar() {
		return new ArrayList<>(filas.values());
	}

	@Override
	public Optional<T> buscarPorId(Integer id) {
		return id == null ? Optional.empty() : Optional.ofNullable(filas.get(id));
	}

	@Override
	public T guardar(T entidad) {
		if (leerId.apply(entidad) == null) {
			escribirId.accept(entidad, siguienteId++);
		}
		filas.put(leerId.apply(entidad), entidad);
		return entidad;
	}

	@Override
	public void eliminar(Integer id) {
		filas.remove(id);
	}
}
