package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Rol;
import com.bodega.control.presentacion.dto.request.RolRequestDto;
import com.bodega.control.presentacion.dto.response.RolResponseDto;

@Mapper(componentModel = "spring")
public interface IRolDtoMapper {
	
	Rol toDomain (RolRequestDto dto);
	
	RolResponseDto toResponseDto (Rol RolPojo);

}
