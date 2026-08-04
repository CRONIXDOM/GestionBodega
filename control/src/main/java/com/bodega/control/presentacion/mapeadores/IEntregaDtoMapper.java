package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Entrega;
import com.bodega.control.presentacion.dto.request.EntregaRequestDto;
import com.bodega.control.presentacion.dto.response.EntregaResponseDto;

@Mapper(componentModel = "spring")
public interface IEntregaDtoMapper {
	
	Entrega toDomain (EntregaRequestDto dto);
	
	EntregaResponseDto toResponseDto (Entrega entregaPojo);

}
