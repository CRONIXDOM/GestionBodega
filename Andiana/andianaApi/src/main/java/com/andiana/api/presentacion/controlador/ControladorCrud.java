package com.andiana.api.presentacion.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.andiana.api.aplicacion.puerto.CasoUsoCrud;

/**
 * Las cuatro operaciones que expone cualquier entidad. El controlador solo
 * traduce HTTP: no decide nada, para eso llama al caso de uso.
 */
public abstract class ControladorCrud<T> {

	private final CasoUsoCrud<T> casoUso;

	protected ControladorCrud(CasoUsoCrud<T> casoUso) {
		this.casoUso = casoUso;
	}

	@GetMapping
	public List<T> listar() {
		return casoUso.listar();
	}

	@GetMapping("/{id}")
	public T buscarPorId(@PathVariable Integer id) {
		return casoUso.buscarPorId(id);
	}

	@PostMapping
	public T guardar(@RequestBody T entidad) {
		return casoUso.guardar(entidad);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
		casoUso.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
