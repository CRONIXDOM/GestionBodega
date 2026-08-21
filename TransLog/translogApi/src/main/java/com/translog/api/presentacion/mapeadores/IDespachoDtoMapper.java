package com.translog.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.presentacion.dto.request.DespachoRequestDto;
import com.translog.api.presentacion.dto.response.DespachoResponseDto;

@Mapper(componentModel = "spring")
public interface IDespachoDtoMapper {

	Despacho toDomain(DespachoRequestDto dto);

	DespachoResponseDto toResponseDto(Despacho despacho);

}
