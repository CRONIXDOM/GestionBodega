package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;
import com.andiana.api.infraestructura.persistencia.jpa.MovimientoMateriaPrimaEntity;

@Mapper(componentModel = "spring")
public interface IMovimientoMateriaPrimaJpaMapper {

	MovimientoMateriaPrima toDominio(MovimientoMateriaPrimaEntity entity);

	MovimientoMateriaPrimaEntity toEntity(MovimientoMateriaPrima nuevoMovimientoMateriaPrima);

}
