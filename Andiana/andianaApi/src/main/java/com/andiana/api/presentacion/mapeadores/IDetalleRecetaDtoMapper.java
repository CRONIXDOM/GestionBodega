package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.presentacion.dto.request.DetalleRecetaRequestDto;
import com.andiana.api.presentacion.dto.response.DetalleRecetaResponseDto;

@Mapper(componentModel = "spring")
public interface IDetalleRecetaDtoMapper {

	DetalleReceta toDomain(DetalleRecetaRequestDto dto);

	DetalleRecetaResponseDto toResponseDto(DetalleReceta detalleRecetaPojo);

}
