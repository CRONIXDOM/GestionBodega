package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.infraestructura.persistencia.jpa.DetalleRecetaEntity;

@Mapper(componentModel = "spring")
public interface IDetalleRecetaJpaMapper {

	DetalleReceta toDominio(DetalleRecetaEntity entity);

	DetalleRecetaEntity toEntity(DetalleReceta nuevoDetalleReceta);

}
