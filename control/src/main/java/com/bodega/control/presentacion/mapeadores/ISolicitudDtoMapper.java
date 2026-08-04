package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.Solicitud;
import com.bodega.control.presentacion.dto.request.SolicitudRequestDto;
import com.bodega.control.presentacion.dto.response.SolicitudResponseDto;


@Mapper(componentModel = "spring")
public interface ISolicitudDtoMapper {

	@Mapping(target = "usuarioRol.idUsuarioRol", source = "idUsuarioRol")
	Solicitud toDomain (SolicitudRequestDto dto);

	@Mapping(target = "idUsuarioRol", source = "usuarioRol.idUsuarioRol")
	SolicitudResponseDto toResponseDto (Solicitud SolicitudPojo);

}
