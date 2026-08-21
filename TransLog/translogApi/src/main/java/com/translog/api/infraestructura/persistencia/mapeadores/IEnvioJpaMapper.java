package com.translog.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Envio;
import com.translog.api.infraestructura.persistencia.jpa.EnvioEntity;

@Mapper(componentModel = "spring")
public interface IEnvioJpaMapper {

	Envio toDominio(EnvioEntity entity);

	EnvioEntity toEntity(Envio nuevoEnvio);

}
