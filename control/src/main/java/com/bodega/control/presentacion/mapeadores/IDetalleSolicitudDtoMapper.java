package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.presentacion.dto.request.DetalleSolicitudRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleSolicitudResponseDto;

@Mapper (componentModel = "spring")
public interface IDetalleSolicitudDtoMapper {

	@Mapping(target = "producto.idProducto", source = "idProducto")
	DetalleSolicitud toDomain (DetalleSolicitudRequestDto dto);

	@Mapping(target = "idProducto", source = "producto.idProducto")
	DetalleSolicitudResponseDto toResponseDto (DetalleSolicitud detalleSolicitudPojo);


}
