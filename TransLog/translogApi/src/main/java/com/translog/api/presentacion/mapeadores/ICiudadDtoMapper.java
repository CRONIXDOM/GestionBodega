package com.translog.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.presentacion.dto.request.CiudadRequestDto;
import com.translog.api.presentacion.dto.response.CiudadResponseDto;

@Mapper(componentModel = "spring")
public interface ICiudadDtoMapper {

	Ciudad toDomain(CiudadRequestDto dto);

	CiudadResponseDto toResponseDto(Ciudad ciudad);

}
