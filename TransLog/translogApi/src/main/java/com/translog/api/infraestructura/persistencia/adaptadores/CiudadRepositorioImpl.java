package com.translog.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.dominio.repositorio.ICiudadRepositorio;
import com.translog.api.infraestructura.persistencia.jpa.CiudadEntity;
import com.translog.api.infraestructura.persistencia.mapeadores.ICiudadJpaMapper;
import com.translog.api.infraestructura.repositorio.ICiudadJpaRepositorio;

public class CiudadRepositorioImpl implements ICiudadRepositorio {

	private final ICiudadJpaRepositorio jpaRepositorio;
	private final ICiudadJpaMapper entityMapper;

	public CiudadRepositorioImpl(ICiudadJpaRepositorio jpaRepositorio, ICiudadJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Ciudad guardar(Ciudad nuevoCiudad) {
		CiudadEntity guardado = jpaRepositorio.save(entityMapper.toEntity(nuevoCiudad));
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Ciudad> buscarPorId(int idCiudad) {
		return jpaRepositorio.findById(idCiudad).map(entityMapper::toDominio);
	}

	@Override
	public List<Ciudad> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idCiudad) {
		jpaRepositorio.deleteById(idCiudad);
	}

}
