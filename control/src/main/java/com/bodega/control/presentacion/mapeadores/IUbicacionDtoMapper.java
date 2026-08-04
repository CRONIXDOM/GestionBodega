package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.presentacion.dto.request.UbicacionRequestDto;
import com.bodega.control.presentacion.dto.response.UbicacionResponseDto;


@Mapper(componentModel = "spring")
public interface IUbicacionDtoMapper {

	@Mapping(target = "zona.idZona", source = "idZona")
	@Mapping(target = "sede.idSede", source = "idSede")
	Ubicacion toDomain (UbicacionRequestDto dto);

	@Mapping(target = "idZona", source = "zona.idZona")
	@Mapping(target = "idSede", source = "sede.idSede")
	UbicacionResponseDto toResponseDto (Ubicacion UbicacionPojo);

}
