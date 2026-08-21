package com.translog.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.dominio.repositorio.IVehiculoRepositorio;
import com.translog.api.infraestructura.persistencia.jpa.VehiculoEntity;
import com.translog.api.infraestructura.persistencia.mapeadores.IVehiculoJpaMapper;
import com.translog.api.infraestructura.repositorio.IVehiculoJpaRepositorio;

public class VehiculoRepositorioImpl implements IVehiculoRepositorio {

	private final IVehiculoJpaRepositorio jpaRepositorio;
	private final IVehiculoJpaMapper entityMapper;

	public VehiculoRepositorioImpl(IVehiculoJpaRepositorio jpaRepositorio, IVehiculoJpaMapper entityMapper) {
		this.jpaRepositorio = jpaRepositorio;
		this.entityMapper = entityMapper;
	}

	@Override
	public Vehiculo guardar(Vehiculo nuevoVehiculo) {
		VehiculoEntity guardado = jpaRepositorio.save(entityMapper.toEntity(nuevoVehiculo));
		return entityMapper.toDominio(guardado);
	}

	@Override
	public Optional<Vehiculo> buscarPorId(int idVehiculo) {
		return jpaRepositorio.findById(idVehiculo).map(entityMapper::toDominio);
	}

	@Override
	public List<Vehiculo> listarTodos() {
		return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
	}

	@Override
	public void eliminar(int idVehiculo) {
		jpaRepositorio.deleteById(idVehiculo);
	}

}
