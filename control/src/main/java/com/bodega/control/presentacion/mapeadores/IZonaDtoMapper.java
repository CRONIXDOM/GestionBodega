package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Zona;
import com.bodega.control.presentacion.dto.request.ZonaRequestDto;
import com.bodega.control.presentacion.dto.response.ZonaResponseDto;

@Mapper(componentModel = "spring")
public interface IZonaDtoMapper {
	
	Zona toDomain (ZonaRequestDto dto);
	
	ZonaResponseDto toResponseDto (Zona ZonaPojo);

}
