package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Zona;
import com.bodega.control.infraestructura.persistencia.jpa.ZonaEntity;

@Mapper (componentModel = "spring")
public interface IZonaJpaMapper {
	
	Zona toDominio (ZonaEntity entity);
	
	ZonaEntity toEntity (Zona nuevaZona);
}
