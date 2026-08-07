package com.andiana.api.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.presentacion.dto.request.MateriaPrimaRequestDto;
import com.andiana.api.presentacion.dto.response.MateriaPrimaResponseDto;

@Mapper(componentModel = "spring")
public interface IMateriaPrimaDtoMapper {

	MateriaPrima toDomain(MateriaPrimaRequestDto dto);

	MateriaPrimaResponseDto toResponseDto(MateriaPrima materiaPrimaPojo);

}
