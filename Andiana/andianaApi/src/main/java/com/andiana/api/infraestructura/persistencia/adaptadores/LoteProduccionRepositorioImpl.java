package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.LoteProduccion;
import com.andiana.api.dominio.repositorio.ILoteProduccionRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.LoteProduccionEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.ILoteProduccionJpaMapper;
import com.andiana.api.infraestructura.repositorio.ILoteProduccionJpaRepositorio;

public class LoteProduccionRepositorioImpl implements ILoteProduccionRepositorio {

	private final ILoteProduccionJpaRepositorio jpaRepositorio;
	private final ILoteProduccionJpaMapper entityMapper;

	public LoteProduccionRepositorioImpl(ILoteProduccionJpaRepositorio jpaRepositorio,
			ILoteProduccionJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public LoteProduccion guardar(LoteProduccion nuevoLoteProduccion) {
		LoteProduccionEntity entidad = entityMapper.toEntity(nuevoLoteProduccion);
		LoteProduccionEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<LoteProduccion> buscarPorid(int idLoteProduccion) {
		return jpaRepositorio.findById(idLoteProduccion).map(entityMapper::toDominio);
	}

	@Override
	public List<LoteProduccion> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idLoteProduccion) {
		jpaRepositorio.deleteById(idLoteProduccion);
	}

	@Override
	public List<LoteProduccion> buscarPorOrden(int idOrden) {
		return jpaRepositorio.findByIdOrden(idOrden).stream().map(entityMapper::toDominio).toList();
	}
}
