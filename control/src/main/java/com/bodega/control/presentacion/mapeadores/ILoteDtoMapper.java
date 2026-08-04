package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.presentacion.dto.request.LoteRequestDto;
import com.bodega.control.presentacion.dto.response.LoteResponseDto;

@Mapper(componentModel = "spring")
public interface ILoteDtoMapper {

	@Mapping(target = "producto.idProducto", source = "idProducto")
	Lote toDomain (LoteRequestDto dto);

	@Mapping(target = "idProducto", source = "producto.idProducto")
	LoteResponseDto toResponseDto (Lote lotePojo);

}
