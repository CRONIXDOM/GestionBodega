package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.LoteEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.ILoteJpaMapper;
import com.bodega.control.infraestructura.repositorio.ILoteJpaRepositorio;

public class LoteRepositorioImpl implements ILoteRepositorio {
	
	private final ILoteJpaRepositorio jpaRepositorio;
	private final ILoteJpaMapper entityMapper;

	public LoteRepositorioImpl(ILoteJpaRepositorio jpaRepositorio, ILoteJpaMapper entityMapper) {
		super();
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Lote guardar(Lote nuevaLote) {
		LoteEntity entidad = entityMapper.toEntity(nuevaLote);
		LoteEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Lote> buscarPorid(int idLote) {
		return jpaRepositorio.findById(idLote).map(entityMapper::toDominio);
	}

	@Override
	public List<Lote> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idLote) {
		jpaRepositorio.deleteById(idLote);
	}

	@Override
	public List<Lote> buscarPorProductoOrdenadoFifo(int idProducto) {
		return jpaRepositorio.buscarPorProductoOrdenadoFifo(idProducto).stream().map(entityMapper::toDominio).toList();
	}

}
