package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Tipo;
import com.bodega.control.dominio.repositorio.ITipoRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.TipoEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.ITipoJpaMapper;
import com.bodega.control.infraestructura.repositorio.ITipoJpaRepositorio;

public class TipoRepositorioImpl implements ITipoRepositorio {

	private final ITipoJpaRepositorio jpaRepositorio;
	private final ITipoJpaMapper entityMapper;

	public TipoRepositorioImpl(ITipoJpaRepositorio jpaRepositorio, ITipoJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Tipo guardar(Tipo nuevoTipo) {
		TipoEntity entidad = entityMapper.toEntity(nuevoTipo);
		TipoEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Tipo> buscarPorid(int idTipo) {
		return jpaRepositorio.findById(idTipo).map(entityMapper::toDominio);
	}

	@Override
	public List<Tipo> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idTipo) {
		jpaRepositorio.deleteById(idTipo);
	}

}