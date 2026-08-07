package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaProduccionRepositorio;

public class ServicioProducto extends ServicioCrud<Producto> {

	private final ProductoRepositorio productos;
	private final RecetaProduccionRepositorio recetas;

	public ServicioProducto(ProductoRepositorio productos, RecetaProduccionRepositorio recetas) {
		super(productos, "Producto");
		this.productos = productos;
		this.recetas = recetas;
	}

	@Override
	protected void validar(Producto producto) {
		producto.setNombre(Validar.normalizar(producto.getNombre()));
		producto.setTipo(Validar.normalizar(producto.getTipo()));
		producto.setPresentacion(Validar.normalizar(producto.getPresentacion()));

		Validar.obligatorio(producto.getNombre(), "nombre");
		Validar.obligatorio(producto.getTipo(), "tipo");
		Validar.obligatorio(producto.getPresentacion(), "presentacion");
		Validar.mayorQueCero(producto.getVolumenMl(), "volumen en ml");
		if (producto.getEstado() == null) {
			producto.setEstado(Boolean.TRUE);
		}

		// la base exige que nombre + presentacion no se repitan (uk_producto)
		Validar.noRepetido(productos.listar(), Producto::getIdProducto,
				p -> p.getNombre() + " | " + p.getPresentacion(), producto.getIdProducto(),
				producto.getNombre() + " | " + producto.getPresentacion(), "el producto");
	}

	@Override
	public void eliminar(Integer id) {
		if (!recetas.buscarPorProducto(id).isEmpty()) {
			throw new ReglaNegocioException(
					"No se puede eliminar el producto: tiene recetas registradas. Eliminalas primero.");
		}
		super.eliminar(id);
	}
}
