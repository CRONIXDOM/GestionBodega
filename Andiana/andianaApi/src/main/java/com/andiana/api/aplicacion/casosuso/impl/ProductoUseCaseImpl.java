package com.andiana.api.aplicacion.casosuso.impl;

import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.IProductoUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.Producto;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;

public class ProductoUseCaseImpl implements IProductoUseCase {

	private final IProductoRepositorio repositorio;
	private final IRecetaProduccionRepositorio recetaRepositorio;

	public ProductoUseCaseImpl(IProductoRepositorio repositorio, IRecetaProduccionRepositorio recetaRepositorio) {
		this.repositorio = repositorio;
		this.recetaRepositorio = recetaRepositorio;
	}

	@Override
	public Producto guardar(Producto nuevoProducto) {
		nuevoProducto.setNombre(Validaciones.normalizar(nuevoProducto.getNombre()));
		nuevoProducto.setTipo(Validaciones.normalizar(nuevoProducto.getTipo()));
		nuevoProducto.setPresentacion(Validaciones.normalizar(nuevoProducto.getPresentacion()));

		Validaciones.obligatorio(nuevoProducto.getNombre(), "nombre");
		Validaciones.obligatorio(nuevoProducto.getTipo(), "tipo");
		Validaciones.obligatorio(nuevoProducto.getPresentacion(), "presentación");
		Validaciones.mayorQueCero(nuevoProducto.getVolumenMl(), "volumen en ml");

		if (nuevoProducto.getEstado() == null) {
			nuevoProducto.setEstado(Boolean.TRUE);
		}
		Validaciones.noRepetido(repositorio.listarTodos(), Producto::getIdProducto,
				p -> p.getNombre() + " | " + p.getPresentacion(), nuevoProducto.getIdProducto(),
				nuevoProducto.getNombre() + " | " + nuevoProducto.getPresentacion(), "el producto");

		return repositorio.guardar(nuevoProducto);
	}

	@Override
	public Producto buscarPorId(int idProducto) {
		return repositorio.buscarPorid(idProducto).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
	}

	@Override
	public List<Producto> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idProducto) {
		if (!recetaRepositorio.buscarPorProducto(idProducto).isEmpty()) {
			throw new RuntimeException(
					"No se puede eliminar el producto: tiene recetas registradas. Elimínalas primero.");
		}
		buscarPorId(idProducto);
		repositorio.eliminar(idProducto);
	}
}
