package com.translog.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.infraestructura.persistencia.jpa.CiudadEntity;

@Mapper(componentModel = "spring")
public interface ICiudadJpaMapper {

	Ciudad toDominio(CiudadEntity entity);

	CiudadEntity toEntity(Ciudad nuevoCiudad);

}
