package com.andiana.api.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.andiana.api.dominio.entidades.Producto;
import com.andiana.api.infraestructura.persistencia.jpa.ProductoEntity;

@Mapper(componentModel = "spring")
public interface IProductoJpaMapper {

	Producto toDominio(ProductoEntity entity);

	ProductoEntity toEntity(Producto nuevoProducto);

}
