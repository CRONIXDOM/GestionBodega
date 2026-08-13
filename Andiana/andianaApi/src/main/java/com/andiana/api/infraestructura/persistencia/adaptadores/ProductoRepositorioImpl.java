package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.Producto;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.ProductoEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IProductoJpaMapper;
import com.andiana.api.infraestructura.repositorio.IProductoJpaRepositorio;

public class ProductoRepositorioImpl implements IProductoRepositorio {

	private final IProductoJpaRepositorio jpaRepositorio;
	private final IProductoJpaMapper entityMapper;

	public ProductoRepositorioImpl(IProductoJpaRepositorio jpaRepositorio, IProductoJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Producto guardar(Producto nuevoProducto) {
		ProductoEntity entidad = entityMapper.toEntity(nuevoProducto);
		ProductoEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Producto> buscarPorId(int idProducto) {
		return jpaRepositorio.findById(idProducto).map(entityMapper::toDominio);
	}

	@Override
	public List<Producto> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idProducto) {
		jpaRepositorio.deleteById(idProducto);
	}
}
