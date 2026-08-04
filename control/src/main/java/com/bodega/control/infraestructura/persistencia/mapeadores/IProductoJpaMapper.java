package com.bodega.control.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;

import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.infraestructura.persistencia.jpa.ProductoEntity;

@Mapper (componentModel = "spring")
public interface IProductoJpaMapper {
	
	Producto toDominio (ProductoEntity entity);
	
	ProductoEntity toEntity (Producto nuevoProducto);

}
