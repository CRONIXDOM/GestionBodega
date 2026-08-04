package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.presentacion.dto.request.DetalleEntregaRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleEntregaResponseDto;

@Mapper(componentModel = "spring")
public interface IDetalleEntregaDtoMapper {

	@Mapping(target = "producto.idProducto", source = "idProducto")
	@Mapping(target = "entrega.idEntrega", source = "idEntrega")
	@Mapping(target = "detalleSolicitud.idDetalleSolicitud", source = "idDetalleSolicitud")
	@Mapping(target = "lote.idLote", source = "idLote")
	DetalleEntrega toDomain (DetalleEntregaRequestDto dto);

	@Mapping(target = "idProducto", source = "producto.idProducto")
	@Mapping(target = "idEntrega", source = "entrega.idEntrega")
	@Mapping(target = "idDetalleSolicitud", source = "detalleSolicitud.idDetalleSolicitud")
	@Mapping(target = "idLote", source = "lote.idLote")
	DetalleEntregaResponseDto toResponseDto (DetalleEntrega detalleEntregaPojo);

}
