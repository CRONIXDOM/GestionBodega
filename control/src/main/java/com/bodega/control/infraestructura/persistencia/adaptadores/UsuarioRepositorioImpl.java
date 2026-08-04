package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.dominio.repositorio.IUsuarioRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.UsuarioEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IUsuarioJpaMapper;
import com.bodega.control.infraestructura.repositorio.IUsuarioJpaRepositorio;

public class UsuarioRepositorioImpl implements IUsuarioRepositorio {

	private final IUsuarioJpaRepositorio jpaRepositorio;
	private final IUsuarioJpaMapper entityMapper;

	public UsuarioRepositorioImpl(IUsuarioJpaRepositorio jpaRepositorio, IUsuarioJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Usuario guardar(Usuario nuevoUsuario) {
		UsuarioEntity entidad = entityMapper.toEntity(nuevoUsuario);
		UsuarioEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Usuario> buscarPorid(int idUsuario) {
		return jpaRepositorio.findById(idUsuario).map(entityMapper::toDominio);
	}

	@Override
	public List<Usuario> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idUsuario) {
		jpaRepositorio.deleteById(idUsuario);
	}

}