package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.DetalleRecetaEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IDetalleRecetaJpaMapper;
import com.andiana.api.infraestructura.repositorio.IDetalleRecetaJpaRepositorio;

public class DetalleRecetaRepositorioImpl implements IDetalleRecetaRepositorio {

	private final IDetalleRecetaJpaRepositorio jpaRepositorio;
	private final IDetalleRecetaJpaMapper entityMapper;

	public DetalleRecetaRepositorioImpl(IDetalleRecetaJpaRepositorio jpaRepositorio,
			IDetalleRecetaJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public DetalleReceta guardar(DetalleReceta nuevoDetalleReceta) {
		DetalleRecetaEntity entidad = entityMapper.toEntity(nuevoDetalleReceta);
		DetalleRecetaEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<DetalleReceta> buscarPorid(int idDetalleReceta) {
		return jpaRepositorio.findById(idDetalleReceta).map(entityMapper::toDominio);
	}

	@Override
	public List<DetalleReceta> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idDetalleReceta) {
		jpaRepositorio.deleteById(idDetalleReceta);
	}

	@Override
	public List<DetalleReceta> buscarPorReceta(int idReceta) {
		return jpaRepositorio.findByIdReceta(idReceta).stream().map(entityMapper::toDominio).toList();
	}
}
