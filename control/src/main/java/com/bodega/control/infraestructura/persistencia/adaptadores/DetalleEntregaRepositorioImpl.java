package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.dominio.repositorio.IDetalleEntregaRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.DetalleEntregaEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IDetalleEntregaJpaMapper;
import com.bodega.control.infraestructura.repositorio.IDetalleEntregaJpaRepositorio;

public class DetalleEntregaRepositorioImpl implements IDetalleEntregaRepositorio {
	
	private final IDetalleEntregaJpaRepositorio jpaRepositorio;
	private final IDetalleEntregaJpaMapper entityMapper;

	public DetalleEntregaRepositorioImpl(IDetalleEntregaJpaRepositorio jpaRepositorio,
			IDetalleEntregaJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public DetalleEntrega guardar(DetalleEntrega nuevaDetalleEntrega) {
		DetalleEntregaEntity entidad = entityMapper.toEntity(nuevaDetalleEntrega);
		DetalleEntregaEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<DetalleEntrega> buscarPorid(int idDetalleEntrega) {
		return jpaRepositorio.findById(idDetalleEntrega).map(entityMapper::toDominio);
	}

	@Override
	public List<DetalleEntrega> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idDetalleEntrega) {
		jpaRepositorio.deleteById(idDetalleEntrega);
		
	}

}
