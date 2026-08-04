package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.dominio.repositorio.IUbicacionRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.UbicacionEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IUbicacionJpaMapper;
import com.bodega.control.infraestructura.repositorio.IUbicacionJpaRepositorio;

public class UbicacionRepositorioImpl implements IUbicacionRepositorio {

	private final IUbicacionJpaRepositorio jpaRepositorio;
	private final IUbicacionJpaMapper entityMapper;

	public UbicacionRepositorioImpl(IUbicacionJpaRepositorio jpaRepositorio, IUbicacionJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Ubicacion guardar(Ubicacion nuevaUbicacion) {
		UbicacionEntity entidad = entityMapper.toEntity(nuevaUbicacion);
		UbicacionEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Ubicacion> buscarPorid(int idUbicacion) {
		return jpaRepositorio.findById(idUbicacion).map(entityMapper::toDominio);
	}

	@Override
	public List<Ubicacion> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idUbicacion) {
		jpaRepositorio.deleteById(idUbicacion);
	}

}