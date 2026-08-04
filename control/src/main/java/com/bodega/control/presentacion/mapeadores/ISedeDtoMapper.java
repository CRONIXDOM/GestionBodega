package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Sede;
import com.bodega.control.presentacion.dto.request.SedeRequestDto;
import com.bodega.control.presentacion.dto.response.SedeResponseDto;

@Mapper(componentModel = "spring")
public interface ISedeDtoMapper {

	Sede toDomain(SedeRequestDto dto);

	SedeResponseDto toResponseDto(Sede sedePojo);

}
