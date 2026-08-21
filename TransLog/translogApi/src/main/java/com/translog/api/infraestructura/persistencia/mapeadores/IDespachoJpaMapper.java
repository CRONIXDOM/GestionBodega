package com.translog.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.infraestructura.persistencia.jpa.DespachoEntity;

@Mapper(componentModel = "spring")
public interface IDespachoJpaMapper {

	Despacho toDominio(DespachoEntity entity);

	DespachoEntity toEntity(Despacho nuevoDespacho);

}
