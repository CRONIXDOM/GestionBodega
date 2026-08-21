package com.translog.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.presentacion.dto.request.ConductorRequestDto;
import com.translog.api.presentacion.dto.response.ConductorResponseDto;

@Mapper(componentModel = "spring")
public interface IConductorDtoMapper {

	Conductor toDomain(ConductorRequestDto dto);

	ConductorResponseDto toResponseDto(Conductor conductor);

}
