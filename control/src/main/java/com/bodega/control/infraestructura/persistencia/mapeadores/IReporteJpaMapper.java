package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Reporte;
import com.bodega.control.infraestructura.persistencia.jpa.ReporteEntity;

@Mapper (componentModel = "spring", uses = { IUsuarioRolJpaMapper.class })
public interface IReporteJpaMapper {
	
	Reporte toDominio (ReporteEntity guardado);

	ReporteEntity toEntity(Reporte nuevoReporte);
	
	
	

}
