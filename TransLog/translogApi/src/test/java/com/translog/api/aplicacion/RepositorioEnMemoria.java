package com.translog.api.aplicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Un almacén de datos falso, en un HashMap. Sirve para probar los casos de uso
 * sin levantar Spring ni PostgreSQL: justamente eso es lo que permite tener el
 * dominio separado del framework.
 *
 * Guarda y devuelve COPIAS, igual que haría una base de datos. Si compartiera
 * la misma instancia, editar un objeto ya guardado cambiaría el "antes" y el
 * "después" a la vez, y las pruebas dejarían pasar errores que en producción sí
 * se notan.
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

	/** Una copia independiente, como la que devolvería una consulta a la base. */
	protected abstract T copiar(T entidad);

	public List<T> listarTodos() {
		List<T> copias = new ArrayList<>();
		for (T fila : filas.values()) {
			copias.add(copiar(fila));
		}
		return copias;
	}

	public Optional<T> buscarPorId(int id) {
		return Optional.ofNullable(filas.get(id)).map(this::copiar);
	}

	public T guardar(T entidad) {
		if (leerId.apply(entidad) == null) {
			escribirId.accept(entidad, siguienteId++);
		}
		filas.put(leerId.apply(entidad), copiar(entidad));
		return entidad;
	}

	public void eliminar(int id) {
		filas.remove(id);
	}
}
