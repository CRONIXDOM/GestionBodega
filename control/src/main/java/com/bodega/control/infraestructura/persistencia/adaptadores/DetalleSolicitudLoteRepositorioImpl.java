package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;

import com.bodega.control.dominio.entidades.DetalleSolicitudLote;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudLoteRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.DetalleSolicitudLoteEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IDetalleSolicitudLoteJpaMapper;
import com.bodega.control.infraestructura.repositorio.IDetalleSolicitudLoteJpaRepositorio;

public class DetalleSolicitudLoteRepositorioImpl implements IDetalleSolicitudLoteRepositorio {

	private final IDetalleSolicitudLoteJpaRepositorio jpaRepositorio;
	private final IDetalleSolicitudLoteJpaMapper entityMapper;

	public DetalleSolicitudLoteRepositorioImpl(IDetalleSolicitudLoteJpaRepositorio jpaRepositorio,
			IDetalleSolicitudLoteJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public DetalleSolicitudLote guardar(DetalleSolicitudLote asignacion) {
		DetalleSolicitudLoteEntity entidad = entityMapper.toEntity(asignacion);
		DetalleSolicitudLoteEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public List<DetalleSolicitudLote> buscarPorDetalleSolicitud(int idDetalleSolicitud) {
		return jpaRepositorio.buscarPorDetalleSolicitud(idDetalleSolicitud).stream().map(entityMapper::toDominio)
				.toList();
	}

}
