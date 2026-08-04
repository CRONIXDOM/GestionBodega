package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Sede;
import com.bodega.control.infraestructura.persistencia.jpa.SedeEntity;

@Mapper(componentModel = "spring")
public interface ISedeJpaMapper {

	Sede toDominio(SedeEntity entity);

	SedeEntity toEntity(Sede nuevaSede);
}
