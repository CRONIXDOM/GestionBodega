package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.presentacion.dto.request.DetalleSolicitudRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleSolicitudResponseDto;

@Mapper (componentModel = "spring")
public interface IDetalleSolicitudDtoMapper {

	@Mapping(target = "producto.idProducto", source = "idProducto")
	@Mapping(target = "solicitud.idSolicitud", source = "idSolicitud")
	DetalleSolicitud toDomain (DetalleSolicitudRequestDto dto);

	@Mapping(target = "idProducto", source = "producto.idProducto")
	@Mapping(target = "idSolicitud", source = "solicitud.idSolicitud")
	DetalleSolicitudResponseDto toResponseDto (DetalleSolicitud detalleSolicitudPojo);


}
