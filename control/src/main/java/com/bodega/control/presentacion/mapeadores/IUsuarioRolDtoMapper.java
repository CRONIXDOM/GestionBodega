package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bodega.control.dominio.entidades.UsuarioRol;
import com.bodega.control.presentacion.dto.response.UsuarioRolResponseDto;
import com.bodega.control.presentacion.dto.request.UsuarioRolRequestDto;

@Mapper(componentModel = "spring")
public interface IUsuarioRolDtoMapper {

    @Mapping(target = "usuario.idUsuario", source = "idUsuario")
    @Mapping(target = "rol.idRol", source = "idRol")
    UsuarioRol toDominio (UsuarioRolRequestDto request);

    @Mapping(target = "idUsuario", source = "usuario.idUsuario")
    @Mapping(target = "idRol", source = "rol.idRol")
    UsuarioRolResponseDto toResponseDto(UsuarioRol usuarioRol);

}