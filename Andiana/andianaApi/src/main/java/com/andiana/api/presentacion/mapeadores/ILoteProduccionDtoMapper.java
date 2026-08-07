package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.LoteProduccion;
import com.andiana.api.presentacion.dto.request.LoteProduccionRequestDto;
import com.andiana.api.presentacion.dto.response.LoteProduccionResponseDto;

@Mapper(componentModel = "spring")
public interface ILoteProduccionDtoMapper {

	LoteProduccion toDomain(LoteProduccionRequestDto dto);

	LoteProduccionResponseDto toResponseDto(LoteProduccion loteProduccionPojo);

}
