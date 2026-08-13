package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.ControlCalidad;
import com.andiana.api.dominio.repositorio.IControlCalidadRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.ControlCalidadEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IControlCalidadJpaMapper;
import com.andiana.api.infraestructura.repositorio.IControlCalidadJpaRepositorio;

public class ControlCalidadRepositorioImpl implements IControlCalidadRepositorio {

	private final IControlCalidadJpaRepositorio jpaRepositorio;
	private final IControlCalidadJpaMapper entityMapper;

	public ControlCalidadRepositorioImpl(IControlCalidadJpaRepositorio jpaRepositorio,
			IControlCalidadJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public ControlCalidad guardar(ControlCalidad nuevoControlCalidad) {
		ControlCalidadEntity entidad = entityMapper.toEntity(nuevoControlCalidad);
		ControlCalidadEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<ControlCalidad> buscarPorId(int idControlCalidad) {
		return jpaRepositorio.findById(idControlCalidad).map(entityMapper::toDominio);
	}

	@Override
	public List<ControlCalidad> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idControlCalidad) {
		jpaRepositorio.deleteById(idControlCalidad);
	}

	@Override
	public List<ControlCalidad> buscarPorLote(int idLote) {
		return jpaRepositorio.findByIdLoteOrderByFechaControlDescIdControlDesc(idLote).stream()
				.map(entityMapper::toDominio).toList();
	}
}
