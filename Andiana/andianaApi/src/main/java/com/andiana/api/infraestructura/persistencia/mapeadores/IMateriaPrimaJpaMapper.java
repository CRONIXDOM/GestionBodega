package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.infraestructura.persistencia.jpa.MateriaPrimaEntity;

@Mapper(componentModel = "spring")
public interface IMateriaPrimaJpaMapper {

	MateriaPrima toDominio(MateriaPrimaEntity entity);

	MateriaPrimaEntity toEntity(MateriaPrima nuevoMateriaPrima);

}
