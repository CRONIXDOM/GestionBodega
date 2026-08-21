package com.translog.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.infraestructura.persistencia.jpa.VehiculoEntity;

@Mapper(componentModel = "spring")
public interface IVehiculoJpaMapper {

	Vehiculo toDominio(VehiculoEntity entity);

	VehiculoEntity toEntity(Vehiculo nuevoVehiculo);

}
