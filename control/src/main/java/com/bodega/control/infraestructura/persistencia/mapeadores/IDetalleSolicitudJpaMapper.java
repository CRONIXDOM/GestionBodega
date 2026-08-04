package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.infraestructura.persistencia.jpa.DetalleSolicitudEntity;

@Mapper (componentModel = "spring", uses = { IProductoJpaMapper.class })
public interface IDetalleSolicitudJpaMapper {
	
	DetalleSolicitud toDominio (DetalleSolicitudEntity entity);
	
	DetalleSolicitudEntity toEntity (DetalleSolicitud detalleEntrega);

}
