package com.translog.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;
import com.translog.api.infraestructura.persistencia.jpa.DespachoEntity;
import com.translog.api.infraestructura.persistencia.mapeadores.IDespachoJpaMapper;
import com.translog.api.infraestructura.repositorio.IDespachoJpaRepositorio;

public class DespachoRepositorioImpl implements IDespachoRepositorio {

	private final IDespachoJpaRepositorio jpaRepositorio;
	private final IDespachoJpaMapper entityMapper;

	public DespachoRepositorioImpl(IDespachoJpaRepositorio jpaRepositorio, IDespachoJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Despacho guardar(Despacho nuevoDespacho) {
		DespachoEntity guardado = jpaRepositorio.save(entityMapper.toEntity(nuevoDespacho));
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Despacho> buscarPorId(int idDespacho) {
		return jpaRepositorio.findById(idDespacho).map(entityMapper::toDominio);
	}

	@Override
	public List<Despacho> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idDespacho) {
		jpaRepositorio.deleteById(idDespacho);
	}

}
