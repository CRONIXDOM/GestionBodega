package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Rol;
import com.bodega.control.infraestructura.persistencia.jpa.RolEntity;

@Mapper (componentModel = "spring")
public interface IRolJpaMapper {
	
	Rol toDominio (RolEntity entity);
	
	RolEntity toEntity (Rol nuevoRol);

}
