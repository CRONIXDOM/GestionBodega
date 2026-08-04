package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.presentacion.dto.request.DetalleEntregaRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleEntregaResponseDto;

@Mapper(componentModel = "spring")
public interface IDetalleEntregaDtoMapper {
	
	DetalleEntrega toDomain (DetalleEntregaRequestDto dto);
	
	DetalleEntregaResponseDto toResponseDto (DetalleEntrega detalleEntregaPojo);

}
