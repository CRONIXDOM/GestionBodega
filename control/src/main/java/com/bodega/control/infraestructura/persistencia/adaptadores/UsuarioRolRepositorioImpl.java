package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.UsuarioRol;
import com.bodega.control.dominio.repositorio.IUsuarioRolRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.UsuarioRolEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IUsuarioRolJpaMapper;
import com.bodega.control.infraestructura.repositorio.IUsuarioRolJpaRepositorio;

public class UsuarioRolRepositorioImpl implements IUsuarioRolRepositorio {

	private final IUsuarioRolJpaRepositorio jpaRepositorio;
	private final IUsuarioRolJpaMapper entityMapper;

	public UsuarioRolRepositorioImpl(IUsuarioRolJpaRepositorio jpaRepositorio, IUsuarioRolJpaMapper mapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = (IUsuarioRolJpaMapper) mapper;
	}

	@Override
	public UsuarioRol guardar(UsuarioRol nuevoUsuarioRol) {
		UsuarioRolEntity entidad = entityMapper.toEntity(nuevoUsuarioRol);
		UsuarioRolEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<UsuarioRol> buscarPorId(int idUsuarioRol) {
	    return jpaRepositorio.findById(idUsuarioRol)
	            .map(entityMapper::toDominio);
	}
	@Override
	public List<UsuarioRol> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idUsuarioRol) {
		jpaRepositorio.deleteById(idUsuarioRol);
	}

}