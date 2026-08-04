package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.presentacion.dto.request.DetalleEntregaRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleEntregaResponseDto;

@Mapper(componentModel = "spring")
public interface IDetalleEntregaDtoMapper {

	@Mapping(target = "entrega.idEntrega", source = "idEntrega")
	DetalleEntrega toDomain (DetalleEntregaRequestDto dto);

	@Mapping(target = "idEntrega", source = "entrega.idEntrega")
	DetalleEntregaResponseDto toResponseDto (DetalleEntrega detalleEntregaPojo);

}
