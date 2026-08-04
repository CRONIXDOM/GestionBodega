package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Zona;
import com.bodega.control.dominio.repositorio.IZonaRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.ZonaEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IZonaJpaMapper;
import com.bodega.control.infraestructura.repositorio.IZonaJpaRepositorio;

public class ZonaRepositorioImpl implements IZonaRepositorio {

	private final IZonaJpaRepositorio jpaRepositorio;
	private final IZonaJpaMapper entityMapper;

	public ZonaRepositorioImpl(IZonaJpaRepositorio jpaRepositorio, IZonaJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Zona guardar(Zona nuevaZona) {
		ZonaEntity entidad = entityMapper.toEntity(nuevaZona);
		ZonaEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Zona> buscarPorid(int idZona) {
		return jpaRepositorio.findById(idZona).map(entityMapper::toDominio);
	}

	@Override
	public List<Zona> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idZona) {
		jpaRepositorio.deleteById(idZona);
	}

}