package com.andiana.api.infraestructura.adaptador;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.infraestructura.entidad.ProductoEntidad;
import com.andiana.api.infraestructura.jpa.ProductoJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.ProductoMapeador;

@Repository
public class ProductoAdaptador extends AdaptadorCrud<Producto, ProductoEntidad>
		implements ProductoRepositorio {

	public ProductoAdaptador(ProductoJpaRepositorio jpaProducto) {
		super(jpaProducto, ProductoMapeador::aDominio, ProductoMapeador::aEntidad);
	}
}
