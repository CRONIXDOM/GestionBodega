package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Credenciales;
import com.bodega.control.presentacion.dto.request.CredencialesRequestDto;
import com.bodega.control.presentacion.dto.response.CredencialesResponseDto;

@Mapper(componentModel = "spring")
public interface ICredencialesDtoMapper {
	
	Credenciales toDomain(CredencialesRequestDto dto);
	
	CredencialesResponseDto toResponseDto (Credenciales credencialesPojo);

}
