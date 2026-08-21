package com.translog.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Envio;
import com.translog.api.presentacion.dto.request.EnvioRequestDto;
import com.translog.api.presentacion.dto.response.EnvioResponseDto;

@Mapper(componentModel = "spring")
public interface IEnvioDtoMapper {

	Envio toDomain(EnvioRequestDto dto);

	EnvioResponseDto toResponseDto(Envio envio);

}
