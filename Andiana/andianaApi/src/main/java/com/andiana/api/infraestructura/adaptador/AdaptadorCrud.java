package com.andiana.api.infraestructura.adaptador;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.dominio.puerto.Repositorio;

/**
 * Puente entre el puerto que pide el dominio y Spring Data JPA. Todo lo que
 * sabe de bases de datos vive de este lado; el dominio solo ve la interfaz
 * {@link Repositorio}.
 */
public abstract class AdaptadorCrud<D, E> implements Repositorio<D> {

	protected final JpaRepository<E, Integer> jpa;
	private final Function<E, D> aDominio;
	private final Function<D, E> aEntidad;

	protected AdaptadorCrud(JpaRepository<E, Integer> jpa, Function<E, D> aDominio, Function<D, E> aEntidad) {
		this.jpa = jpa;
		this.aDominio = aDominio;
		this.aEntidad = aEntidad;
	}

	@Override
	public List<D> listar() {
		return jpa.findAll().stream().map(aDominio).toList();
	}

	@Override
	public Optional<D> buscarPorId(Integer id) {
		return id == null ? Optional.empty() : jpa.findById(id).map(aDominio);
	}

	@Override
	public D guardar(D modelo) {
		return aDominio.apply(jpa.save(aEntidad.apply(modelo)));
	}

	@Override
	public void eliminar(Integer id) {
		jpa.deleteById(id);
	}

	protected List<D> convertir(List<E> entidades) {
		return entidades.stream().map(aDominio).toList();
	}
}
