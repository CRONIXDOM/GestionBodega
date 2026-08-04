package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.presentacion.dto.request.RegistroRequestDto;
import com.bodega.control.presentacion.dto.response.RegistroResponseDto;

@Mapper(componentModel = "spring")
public interface IRegistroDtoMapper {

	@Mapping(target = "lote.idLote", source = "idLote")
	@Mapping(target = "tipo.idTipo", source = "idTipo")
	@Mapping(target = "ubicacion.idUbicacion", source = "idUbicacion")
	@Mapping(target = "detalleEntrega.idDetalleEntrega", source = "idDetalleEntrega")
	@Mapping(target = "usuarioRol.idUsuarioRol", source = "idUsuarioRol")
	Registro toDomain (RegistroRequestDto dto);

	@Mapping(target = "idLote", source = "lote.idLote")
	@Mapping(target = "idTipo", source = "tipo.idTipo")
	@Mapping(target = "idUbicacion", source = "ubicacion.idUbicacion")
	@Mapping(target = "idDetalleEntrega", source = "detalleEntrega.idDetalleEntrega")
	@Mapping(target = "idUsuarioRol", source = "usuarioRol.idUsuarioRol")
	RegistroResponseDto toResponseDto (Registro RegistroPojo);

}
