package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.LoteProduccion;
import com.andiana.api.infraestructura.persistencia.jpa.LoteProduccionEntity;

@Mapper(componentModel = "spring")
public interface ILoteProduccionJpaMapper {

	LoteProduccion toDominio(LoteProduccionEntity entity);

	LoteProduccionEntity toEntity(LoteProduccion nuevoLoteProduccion);

}
