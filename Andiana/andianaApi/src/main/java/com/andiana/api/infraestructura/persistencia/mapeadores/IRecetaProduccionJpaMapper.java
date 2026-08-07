package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.RecetaProduccion;
import com.andiana.api.infraestructura.persistencia.jpa.RecetaProduccionEntity;

@Mapper(componentModel = "spring")
public interface IRecetaProduccionJpaMapper {

	RecetaProduccion toDominio(RecetaProduccionEntity entity);

	RecetaProduccionEntity toEntity(RecetaProduccion nuevoRecetaProduccion);

}
