package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.MateriaPrimaEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IMateriaPrimaJpaMapper;
import com.andiana.api.infraestructura.repositorio.IMateriaPrimaJpaRepositorio;

public class MateriaPrimaRepositorioImpl implements IMateriaPrimaRepositorio {

	private final IMateriaPrimaJpaRepositorio jpaRepositorio;
	private final IMateriaPrimaJpaMapper entityMapper;

	public MateriaPrimaRepositorioImpl(IMateriaPrimaJpaRepositorio jpaRepositorio,
			IMateriaPrimaJpaMapper entityMapper) {

		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public MateriaPrima guardar(MateriaPrima nuevoMateriaPrima) {
		MateriaPrimaEntity entidad = entityMapper.toEntity(nuevoMateriaPrima);
		MateriaPrimaEntity guardado = jpaRepositorio.save(entidad);
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<MateriaPrima> buscarPorId(int idMateriaPrima) {
		return jpaRepositorio.findById(idMateriaPrima).map(entityMapper::toDominio);
	}

	@Override
	public List<MateriaPrima> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idMateriaPrima) {
		jpaRepositorio.deleteById(idMateriaPrima);
	}
}
