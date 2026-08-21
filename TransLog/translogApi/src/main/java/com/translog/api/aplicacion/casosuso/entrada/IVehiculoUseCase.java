package com.translog.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.translog.api.dominio.entidades.Vehiculo;

public interface IVehiculoUseCase {

	Vehiculo guardar(Vehiculo nuevoVehiculo);

	Vehiculo buscarPorId(int idVehiculo);

	List<Vehiculo> listarTodos();

	void eliminar(int idVehiculo);

}
