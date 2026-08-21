package com.translog.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.infraestructura.persistencia.jpa.ConductorEntity;

@Mapper(componentModel = "spring")
public interface IConductorJpaMapper {

	Conductor toDominio(ConductorEntity entity);

	ConductorEntity toEntity(Conductor nuevoConductor);

}
