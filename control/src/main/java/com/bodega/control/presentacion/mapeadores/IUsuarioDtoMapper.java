package com.bodega.control.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.presentacion.dto.request.UsuarioRequestDto;
import com.bodega.control.presentacion.dto.response.UsuarioResponseDto;


@Mapper(componentModel = "spring")
public interface IUsuarioDtoMapper {
	
	Usuario toDomain (UsuarioRequestDto dto);
	
	UsuarioResponseDto toResponseDto (Usuario UsuarioPojo);

}
