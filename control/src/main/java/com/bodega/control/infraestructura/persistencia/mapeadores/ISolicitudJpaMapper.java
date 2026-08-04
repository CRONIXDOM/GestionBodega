package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Solicitud;
import com.bodega.control.infraestructura.persistencia.jpa.SolicitudEntity;

@Mapper (componentModel = "spring", uses = { IUsuarioRolJpaMapper.class })
public interface ISolicitudJpaMapper {
	
	Solicitud toDominio (SolicitudEntity entity);
	
	SolicitudEntity toEntity (Solicitud nuevaSolicitud);

}
