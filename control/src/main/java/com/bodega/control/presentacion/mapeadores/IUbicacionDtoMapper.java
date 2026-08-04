package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.presentacion.dto.request.UbicacionRequestDto;
import com.bodega.control.presentacion.dto.response.UbicacionResponseDto;


@Mapper(componentModel = "spring")
public interface IUbicacionDtoMapper {
	
	Ubicacion toDomain (UbicacionRequestDto dto);
	
	UbicacionResponseDto toResponseDto (Ubicacion UbicacionPojo);

}
