package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.RecetaProduccion;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.RecetaProduccionEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IRecetaProduccionJpaMapper;
import com.andiana.api.infraestructura.repositorio.IRecetaProduccionJpaRepositorio;

public class RecetaProduccionRepositorioImpl implements IRecetaProduccionRepositorio {

	private final IRecetaProduccionJpaRepositorio jpaRepositorio;
	private final IRecetaProduccionJpaMapper entityMapper;

	public RecetaProduccionRepositorioImpl(IRecetaProduccionJpaRepositorio jpaRepositorio,
			IRecetaProduccionJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public RecetaProduccion guardar(RecetaProduccion nuevoRecetaProduccion) {
		RecetaProduccionEntity entidad = entityMapper.toEntity(nuevoRecetaProduccion);
		RecetaProduccionEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<RecetaProduccion> buscarPorid(int idRecetaProduccion) {
		return jpaRepositorio.findById(idRecetaProduccion).map(entityMapper::toDominio);
	}

	@Override
	public List<RecetaProduccion> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idRecetaProduccion) {
		jpaRepositorio.deleteById(idRecetaProduccion);
	}

	@Override
	public List<RecetaProduccion> buscarPorProducto(int idProducto) {
		return jpaRepositorio.findByIdProductoOrderByVersionAsc(idProducto).stream().map(entityMapper::toDominio)
				.toList();
	}
}
