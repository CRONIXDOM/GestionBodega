package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.ControlCalidad;
import com.andiana.api.infraestructura.persistencia.jpa.ControlCalidadEntity;

@Mapper(componentModel = "spring")
public interface IControlCalidadJpaMapper {

	ControlCalidad toDominio(ControlCalidadEntity entity);

	ControlCalidadEntity toEntity(ControlCalidad nuevoControlCalidad);

}
