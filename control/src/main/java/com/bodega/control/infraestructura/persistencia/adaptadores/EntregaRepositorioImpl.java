package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Entrega;
import com.bodega.control.dominio.repositorio.IEntregaRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.EntregaEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IEntregaJpaMapper;
import com.bodega.control.infraestructura.repositorio.IEntregaJpaRepositorio;

public class EntregaRepositorioImpl implements IEntregaRepositorio {
	
	private final IEntregaJpaRepositorio jpaRepositorio;
	private final IEntregaJpaMapper entityMapper;

	public EntregaRepositorioImpl(IEntregaJpaRepositorio jpaRepositorio, IEntregaJpaMapper entityMapper) {
		super();
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Entrega guardar(Entrega nuevaEntrega) {
		EntregaEntity entidad = entityMapper.toEntity(nuevaEntrega);
		EntregaEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Entrega> buscarPorid(int idEntrega) {
		return jpaRepositorio.findById(idEntrega).map(entityMapper::toDominio);

	}

	@Override
	public List<Entrega> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();

	}

	@Override
	public void eliminar(int idEntrega) {
		jpaRepositorio.deleteById(idEntrega);
		
	}

}
