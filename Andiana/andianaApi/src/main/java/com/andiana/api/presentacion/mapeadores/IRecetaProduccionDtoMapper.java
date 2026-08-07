package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.RecetaProduccion;
import com.andiana.api.presentacion.dto.request.RecetaProduccionRequestDto;
import com.andiana.api.presentacion.dto.response.RecetaProduccionResponseDto;

@Mapper(componentModel = "spring")
public interface IRecetaProduccionDtoMapper {

	RecetaProduccion toDomain(RecetaProduccionRequestDto dto);

	RecetaProduccionResponseDto toResponseDto(RecetaProduccion recetaProduccionPojo);

}
