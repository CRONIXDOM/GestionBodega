package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Sede;
import com.bodega.control.dominio.repositorio.ISedeRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.SedeEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.ISedeJpaMapper;
import com.bodega.control.infraestructura.repositorio.ISedeJpaRepositorio;

public class SedeRepositorioImpl implements ISedeRepositorio {

	private final ISedeJpaRepositorio jpaRepositorio;
	private final ISedeJpaMapper entityMapper;

	public SedeRepositorioImpl(ISedeJpaRepositorio jpaRepositorio, ISedeJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Sede guardar(Sede nuevaSede) {
		SedeEntity entidad = entityMapper.toEntity(nuevaSede);
		SedeEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Sede> buscarPorid(int idSede) {
		return jpaRepositorio.findById(idSede).map(entityMapper::toDominio);
	}

	@Override
	public List<Sede> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idSede) {
		jpaRepositorio.deleteById(idSede);
	}

}
