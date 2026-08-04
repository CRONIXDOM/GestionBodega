package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Tipo;
import com.bodega.control.presentacion.dto.request.TipoRequestDto;
import com.bodega.control.presentacion.dto.response.TipoResponseDto;

@Mapper(componentModel = "spring")
public interface ITipoDtoMapper {
	
	Tipo toDomain (TipoRequestDto dto);
	
	TipoResponseDto toResponseDto (Tipo TipoPojo);

}
