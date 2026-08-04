package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.infraestructura.persistencia.jpa.LoteEntity;

@Mapper (componentModel = "spring", uses = { IProductoJpaMapper.class, IUbicacionJpaMapper.class })
public interface ILoteJpaMapper {
	
	Lote toDominio (LoteEntity entity);
	
	LoteEntity toEntity (Lote nuevaLote);
		

}
