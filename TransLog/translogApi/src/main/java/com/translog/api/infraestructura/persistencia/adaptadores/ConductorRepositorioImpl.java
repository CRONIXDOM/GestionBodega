package com.translog.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.dominio.repositorio.IConductorRepositorio;
import com.translog.api.infraestructura.persistencia.jpa.ConductorEntity;
import com.translog.api.infraestructura.persistencia.mapeadores.IConductorJpaMapper;
import com.translog.api.infraestructura.repositorio.IConductorJpaRepositorio;

public class ConductorRepositorioImpl implements IConductorRepositorio {

	private final IConductorJpaRepositorio jpaRepositorio;
	private final IConductorJpaMapper entityMapper;

	public ConductorRepositorioImpl(IConductorJpaRepositorio jpaRepositorio, IConductorJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Conductor guardar(Conductor nuevoConductor) {
		ConductorEntity guardado = jpaRepositorio.save(entityMapper.toEntity(nuevoConductor));
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Conductor> buscarPorId(int idConductor) {
		return jpaRepositorio.findById(idConductor).map(entityMapper::toDominio);
	}

	@Override
	public List<Conductor> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idConductor) {
		jpaRepositorio.deleteById(idConductor);
	}

}
