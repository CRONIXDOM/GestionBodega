package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.infraestructura.persistencia.jpa.RegistroEntity;

@Mapper (componentModel = "spring", uses = { ILoteJpaMapper.class, ITipoJpaMapper.class, IUbicacionJpaMapper.class,
		IDetalleEntregaJpaMapper.class, IUsuarioRolJpaMapper.class })
public interface IRegistroJpaMapper {
	
	Registro toDominio (RegistroEntity entity);
	
	RegistroEntity toEntity (Registro nuevoRegistro);

}
