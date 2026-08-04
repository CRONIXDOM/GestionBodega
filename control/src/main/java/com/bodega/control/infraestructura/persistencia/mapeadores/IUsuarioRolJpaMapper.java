package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.UsuarioRol;
import com.bodega.control.infraestructura.persistencia.jpa.UsuarioRolEntity;


@Mapper(componentModel = "spring", uses = { IUsuarioJpaMapper.class, IRolJpaMapper.class })
public interface IUsuarioRolJpaMapper {

    UsuarioRol toDominio (UsuarioRolEntity request);

    UsuarioRolEntity toEntity(UsuarioRol usuarioRol);

}