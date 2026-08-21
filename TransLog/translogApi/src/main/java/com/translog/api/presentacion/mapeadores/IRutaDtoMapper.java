package com.translog.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.presentacion.dto.request.RutaRequestDto;
import com.translog.api.presentacion.dto.response.RutaResponseDto;

@Mapper(componentModel = "spring")
public interface IRutaDtoMapper {

	Ruta toDomain(RutaRequestDto dto);

	RutaResponseDto toResponseDto(Ruta ruta);

}
