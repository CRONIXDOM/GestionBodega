package com.translog.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.infraestructura.persistencia.jpa.RutaEntity;

@Mapper(componentModel = "spring")
public interface IRutaJpaMapper {

	Ruta toDominio(RutaEntity entity);

	RutaEntity toEntity(Ruta nuevoRuta);

}
