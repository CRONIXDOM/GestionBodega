package com.translog.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Vehiculo;

public interface IVehiculoRepositorio {

	Vehiculo guardar(Vehiculo nuevoVehiculo);

	Optional<Vehiculo> buscarPorId(int idVehiculo);

	List<Vehiculo> listarTodos();

	void eliminar(int idVehiculo);

}
