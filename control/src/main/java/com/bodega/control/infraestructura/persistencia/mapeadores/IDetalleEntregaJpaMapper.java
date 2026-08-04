package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.DetalleEntrega;

import com.bodega.control.infraestructura.persistencia.jpa.DetalleEntregaEntity;

@Mapper (componentModel = "spring")
public interface IDetalleEntregaJpaMapper {
	
	DetalleEntrega toDominio (DetalleEntregaEntity entity);
	
	DetalleEntregaEntity toEntity (DetalleEntrega detalleEntrega);


}
