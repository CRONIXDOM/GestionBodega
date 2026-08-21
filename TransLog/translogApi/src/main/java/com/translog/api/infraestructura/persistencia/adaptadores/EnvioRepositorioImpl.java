package com.translog.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Envio;
import com.translog.api.dominio.repositorio.IEnvioRepositorio;
import com.translog.api.infraestructura.persistencia.jpa.EnvioEntity;
import com.translog.api.infraestructura.persistencia.mapeadores.IEnvioJpaMapper;
import com.translog.api.infraestructura.repositorio.IEnvioJpaRepositorio;

public class EnvioRepositorioImpl implements IEnvioRepositorio {

	private final IEnvioJpaRepositorio jpaRepositorio;
	private final IEnvioJpaMapper entityMapper;

	public EnvioRepositorioImpl(IEnvioJpaRepositorio jpaRepositorio, IEnvioJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Envio guardar(Envio nuevoEnvio) {
		EnvioEntity guardado = jpaRepositorio.save(entityMapper.toEntity(nuevoEnvio));
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Envio> buscarPorId(int idEnvio) {
		return jpaRepositorio.findById(idEnvio).map(entityMapper::toDominio);
	}

	@Override
	public List<Envio> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idEnvio) {
		jpaRepositorio.deleteById(idEnvio);
	}

}
