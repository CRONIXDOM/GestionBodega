package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.Producto;
import com.andiana.api.presentacion.dto.request.ProductoRequestDto;
import com.andiana.api.presentacion.dto.response.ProductoResponseDto;

@Mapper(componentModel = "spring")
public interface IProductoDtoMapper {

	Producto toDomain(ProductoRequestDto dto);

	ProductoResponseDto toResponseDto(Producto productoPojo);

}
