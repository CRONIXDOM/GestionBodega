package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.infraestructura.persistencia.jpa.UsuarioEntity;

@Mapper (componentModel = "spring")
public interface IUsuarioJpaMapper {
	
	Usuario toDominio (UsuarioEntity entity);
	
	UsuarioEntity toEntity (Usuario nuevoUsuario);

}
