package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Entrega;
import com.bodega.control.infraestructura.persistencia.jpa.EntregaEntity;

@Mapper (componentModel = "spring")
public interface IEntregaJpaMapper {
	
	Entrega toDominio (EntregaEntity entity);
	
	EntregaEntity toEntity (Entrega entrega);

}
