package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.InventarioProducto;
import com.andiana.api.infraestructura.persistencia.jpa.InventarioProductoEntity;

@Mapper(componentModel = "spring")
public interface IInventarioProductoJpaMapper {

	InventarioProducto toDominio(InventarioProductoEntity entity);

	InventarioProductoEntity toEntity(InventarioProducto nuevoInventarioProducto);

}
