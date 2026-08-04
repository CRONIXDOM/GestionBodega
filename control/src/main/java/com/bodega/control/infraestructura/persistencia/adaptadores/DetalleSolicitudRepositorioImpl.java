package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.DetalleSolicitudEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IDetalleSolicitudJpaMapper;
import com.bodega.control.infraestructura.repositorio.IDetalleSolicitudJpaRepositorio;

public class DetalleSolicitudRepositorioImpl implements IDetalleSolicitudRepositorio{
	
	private final IDetalleSolicitudJpaRepositorio jpaRepositorio;
	private final IDetalleSolicitudJpaMapper entityMapper;

	public DetalleSolicitudRepositorioImpl(IDetalleSolicitudJpaRepositorio jpaRepositorio,
			IDetalleSolicitudJpaMapper entityMapper) {
		super();
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public DetalleSolicitud guardar(DetalleSolicitud nuevaDetalleSolicitud) {
		DetalleSolicitudEntity entidad = entityMapper.toEntity(nuevaDetalleSolicitud);
		DetalleSolicitudEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<DetalleSolicitud> buscarPorid(int idDetalleSolicitud) {
		return jpaRepositorio.findById(idDetalleSolicitud).map(entityMapper::toDominio);
	}

	@Override
	public List<DetalleSolicitud> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idDetalleSolicitud) {
		jpaRepositorio.deleteById(idDetalleSolicitud);
		
	}

}
