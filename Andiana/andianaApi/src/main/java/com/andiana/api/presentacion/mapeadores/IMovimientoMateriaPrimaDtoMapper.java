package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;
import com.andiana.api.presentacion.dto.request.MovimientoMateriaPrimaRequestDto;
import com.andiana.api.presentacion.dto.response.MovimientoMateriaPrimaResponseDto;

@Mapper(componentModel = "spring")
public interface IMovimientoMateriaPrimaDtoMapper {

	MovimientoMateriaPrima toDomain(MovimientoMateriaPrimaRequestDto dto);

	MovimientoMateriaPrimaResponseDto toResponseDto(MovimientoMateriaPrima movimientoMateriaPrimaPojo);

}
