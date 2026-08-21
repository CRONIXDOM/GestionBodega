package com.translog.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.presentacion.dto.request.VehiculoRequestDto;
import com.translog.api.presentacion.dto.response.VehiculoResponseDto;

@Mapper(componentModel = "spring")
public interface IVehiculoDtoMapper {

	Vehiculo toDomain(VehiculoRequestDto dto);

	VehiculoResponseDto toResponseDto(Vehiculo vehiculo);

}
