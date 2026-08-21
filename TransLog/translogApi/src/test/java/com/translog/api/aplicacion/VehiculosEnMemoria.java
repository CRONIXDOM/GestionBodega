package com.translog.api.aplicacion;

import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.dominio.repositorio.IVehiculoRepositorio;

public class VehiculosEnMemoria extends RepositorioEnMemoria<Vehiculo> implements IVehiculoRepositorio {

	public VehiculosEnMemoria() {
		super(Vehiculo::getIdVehiculo, Vehiculo::setIdVehiculo);
	}

	@Override
	protected Vehiculo copiar(Vehiculo v) {
		return new Vehiculo(v.getIdVehiculo(), v.getPlaca(), v.getCapacidadMaxima(), v.getEstado());
	}
}
