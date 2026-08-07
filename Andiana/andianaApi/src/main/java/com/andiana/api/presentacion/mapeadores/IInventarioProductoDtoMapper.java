package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.InventarioProducto;
import com.andiana.api.presentacion.dto.request.InventarioProductoRequestDto;
import com.andiana.api.presentacion.dto.response.InventarioProductoResponseDto;

@Mapper(componentModel = "spring")
public interface IInventarioProductoDtoMapper {

	InventarioProducto toDomain(InventarioProductoRequestDto dto);

	InventarioProductoResponseDto toResponseDto(InventarioProducto inventarioProductoPojo);

}
