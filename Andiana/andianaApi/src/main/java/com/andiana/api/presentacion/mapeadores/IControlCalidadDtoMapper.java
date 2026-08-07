package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.ControlCalidad;
import com.andiana.api.presentacion.dto.request.ControlCalidadRequestDto;
import com.andiana.api.presentacion.dto.response.ControlCalidadResponseDto;

@Mapper(componentModel = "spring")
public interface IControlCalidadDtoMapper {

	ControlCalidad toDomain(ControlCalidadRequestDto dto);

	ControlCalidadResponseDto toResponseDto(ControlCalidad controlCalidadPojo);

}
