package com.andiana.api.aplicacion.servicio;

import java.util.List;

import com.andiana.api.aplicacion.puerto.CasoUsoCrud;
import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.puerto.Repositorio;

/**
 * El comportamiento que comparten los nueve casos de uso: listar, buscar,
 * guardar y eliminar. Cada servicio concreto solo escribe lo suyo, que son sus
 * reglas de negocio, sobreescribiendo {@link #validar(Object)}.
 *
 * No lleva ni una anotacion: las instancias se arman a mano en la capa de
 * infraestructura, de modo que esta clase se puede probar con un repositorio
 * falso sin levantar nada.
 */
public abstract class ServicioCrud<T> implements CasoUsoCrud<T> {

	protected final Repositorio<T> repositorio;
	private final String nombreEntidad;

	protected ServicioCrud(Repositorio<T> repositorio, String nombreEntidad) {
		this.repositorio = repositorio;
		this.nombreEntidad = nombreEntidad;
	}

	@Override
	public List<T> listar() {
		return repositorio.listar();
	}

	@Override
	public T buscarPorId(Integer id) {
		return repositorio.buscarPorId(id)
				.orElseThrow(() -> new ReglaNegocioException(nombreEntidad + " no encontrado"));
	}

	@Override
	public T guardar(T entidad) {
		if (entidad == null) {
			throw new ReglaNegocioException("No llegaron los datos de " + nombreEntidad);
		}
		validar(entidad);
		return repositorio.guardar(entidad);
	}

	@Override
	public void eliminar(Integer id) {
		buscarPorId(id);
		repositorio.eliminar(id);
	}

	/** Las reglas propias de cada entidad. Se ejecuta antes de guardar nada. */
	protected abstract void validar(T entidad);
}
