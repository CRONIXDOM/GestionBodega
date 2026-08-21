package com.translog.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.dominio.repositorio.IRutaRepositorio;
import com.translog.api.infraestructura.persistencia.jpa.RutaEntity;
import com.translog.api.infraestructura.persistencia.mapeadores.IRutaJpaMapper;
import com.translog.api.infraestructura.repositorio.IRutaJpaRepositorio;

public class RutaRepositorioImpl implements IRutaRepositorio {

	private final IRutaJpaRepositorio jpaRepositorio;
	private final IRutaJpaMapper entityMapper;

	public RutaRepositorioImpl(IRutaJpaRepositorio jpaRepositorio, IRutaJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Ruta guardar(Ruta nuevoRuta) {
		RutaEntity guardado = jpaRepositorio.save(entityMapper.toEntity(nuevoRuta));
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Ruta> buscarPorId(int idRuta) {
		return jpaRepositorio.findById(idRuta).map(entityMapper::toDominio);
	}

	@Override
	public List<Ruta> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idRuta) {
		jpaRepositorio.deleteById(idRuta);
	}

}
