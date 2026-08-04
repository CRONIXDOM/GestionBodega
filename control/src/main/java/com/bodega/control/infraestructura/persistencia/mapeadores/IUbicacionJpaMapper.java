package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.infraestructura.persistencia.jpa.UbicacionEntity;

@Mapper (componentModel = "spring", uses = { IZonaJpaMapper.class })
public interface IUbicacionJpaMapper {
	
	Ubicacion toDominio (UbicacionEntity entity);
	
	UbicacionEntity toEntity (Ubicacion nuevaUbicacion);

}
