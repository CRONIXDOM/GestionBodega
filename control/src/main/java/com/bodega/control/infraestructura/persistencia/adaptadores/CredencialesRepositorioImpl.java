package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Credenciales;
import com.bodega.control.dominio.repositorio.ICredencialesRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.CredencialesEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.ICredencialesJpaMapper;
import com.bodega.control.infraestructura.repositorio.ICredencialesJpaRepositorio;

public class CredencialesRepositorioImpl implements ICredencialesRepositorio {

	// Dependencias
	private final ICredencialesJpaRepositorio jpaRepositorio;
	private final ICredencialesJpaMapper entityMapper;

	// Constructor
	public CredencialesRepositorioImpl(ICredencialesJpaRepositorio jpaRepositorio,
			ICredencialesJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Credenciales guardar(Credenciales nuevaCredenciales) {
		CredencialesEntity entidad = entityMapper.toEntity(nuevaCredenciales);
		CredencialesEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Credenciales> buscarPorId(int idCredenciales) {
		return jpaRepositorio.findById(idCredenciales).map(entityMapper::toDominio);
	}

	@Override
	public List<Credenciales> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idCredenciales) {
		jpaRepositorio.deleteById(idCredenciales);
	}


	@Override
	public List<Credenciales> listarCredenciales() {
		return jpaRepositorio.listarCredenciales().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public List<Credenciales> buscarCredencialesNombre(String nombre) {
		return jpaRepositorio.buscarCredencialesNombre(nombre).stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public List<Credenciales> buscarCredencialesEstado(String nombre, boolean estado) {
		return jpaRepositorio.buscarCredencialesEstado(nombre, estado).stream().map(entityMapper::toDominio).toList();
	}


}