package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.OrdenProduccion;
import com.andiana.api.infraestructura.persistencia.jpa.OrdenProduccionEntity;

@Mapper(componentModel = "spring")
public interface IOrdenProduccionJpaMapper {

	OrdenProduccion toDominio(OrdenProduccionEntity entity);

	OrdenProduccionEntity toEntity(OrdenProduccion nuevoOrdenProduccion);

}
