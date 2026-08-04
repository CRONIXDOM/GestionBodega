package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.presentacion.dto.request.UbicacionRequestDto;
import com.bodega.control.presentacion.dto.response.UbicacionResponseDto;


@Mapper(componentModel = "spring")
public interface IUbicacionDtoMapper {

	@Mapping(target = "zona.idZona", source = "idZona")
	Ubicacion toDomain (UbicacionRequestDto dto);

	@Mapping(target = "idZona", source = "zona.idZona")
	UbicacionResponseDto toResponseDto (Ubicacion UbicacionPojo);

}
