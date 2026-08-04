package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.Reporte;
import com.bodega.control.presentacion.dto.request.ReporteRequestDto;
import com.bodega.control.presentacion.dto.response.ReporteResponseDto;

@Mapper(componentModel = "spring")
public interface IReporteDtoMapper {

	@Mapping(target = "usuarioRol.idUsuarioRol", source = "idUsuarioRol")
	Reporte toDomain (ReporteRequestDto dto);

	@Mapping(target = "idUsuarioRol", source = "usuarioRol.idUsuarioRol")
	ReporteResponseDto toResponseDto (Reporte ReportePojo);

}
