package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.DetalleSolicitudLote;
import com.bodega.control.infraestructura.persistencia.jpa.DetalleSolicitudLoteEntity;

@Mapper(componentModel = "spring", uses = { IDetalleSolicitudJpaMapper.class, ILoteJpaMapper.class })
public interface IDetalleSolicitudLoteJpaMapper {

	DetalleSolicitudLote toDominio(DetalleSolicitudLoteEntity entity);

	DetalleSolicitudLoteEntity toEntity(DetalleSolicitudLote dominio);

}
