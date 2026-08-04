package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.presentacion.dto.request.ProductoRequestDto;
import com.bodega.control.presentacion.dto.response.ProductoResponseDto;

@Mapper(componentModel = "spring")
public interface IProductoDtoMapper {
	
	Producto toDomain (ProductoRequestDto dto);
	
	ProductoResponseDto toResponseDto (Producto ProductoPojo);

}
