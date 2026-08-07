package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.dominio.puerto.ProductoRepositorio;

public class ServicioProducto extends ServicioCrud<Producto> {

	private final ProductoRepositorio productos;

	public ServicioProducto(ProductoRepositorio productos) {
		super(productos, "Producto");
		this.productos = productos;
	}

	@Override
	protected void validar(Producto producto) {
		producto.setNombre(Validar.normalizar(producto.getNombre()));
		producto.setPresentacion(Validar.normalizar(producto.getPresentacion()));

		Validar.obligatorio(producto.getNombre(), "nombre");
		Validar.unoDe(producto.getPresentacion(), "presentacion",
				"350 ML", "500 ML", "1 LITRO", "2 LITROS");

		// el mismo sabor en la misma presentacion seria el mismo producto
		Validar.noRepetido(productos.listar(), Producto::getIdProducto,
				p -> p.getNombre() + " " + p.getPresentacion(), producto.getIdProducto(),
				producto.getNombre() + " " + producto.getPresentacion(), "el producto");
	}
}
