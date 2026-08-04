package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Credenciales;
import com.bodega.control.infraestructura.persistencia.jpa.CredencialesEntity;

@Mapper (componentModel = "spring")
public interface ICredencialesJpaMapper {
	
	Credenciales toDominio (CredencialesEntity entity);
	
	CredencialesEntity toEntity(Credenciales credenciales);

}
