package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.OrdenProduccion;
import com.andiana.api.presentacion.dto.request.OrdenProduccionRequestDto;
import com.andiana.api.presentacion.dto.response.OrdenProduccionResponseDto;

@Mapper(componentModel = "spring")
public interface IOrdenProduccionDtoMapper {

	OrdenProduccion toDomain(OrdenProduccionRequestDto dto);

	OrdenProduccionResponseDto toResponseDto(OrdenProduccion ordenProduccionPojo);

}
