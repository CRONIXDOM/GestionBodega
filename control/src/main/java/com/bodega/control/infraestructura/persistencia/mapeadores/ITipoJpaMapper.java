package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Tipo;
import com.bodega.control.infraestructura.persistencia.jpa.TipoEntity;

@Mapper (componentModel = "spring")
public interface ITipoJpaMapper {
	
	Tipo toDominio (TipoEntity entity);
	
	TipoEntity toEntity (Tipo nuevoTipo);

}
