package com.andiana.api.presentacion;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.andiana.api.dominio.ReglaNegocioException;

/**
 * Convierte los avisos del dominio en respuestas HTTP con un mensaje legible,
 * para que quien consuma la API reciba el motivo y no un error generico.
 */
@RestControllerAdvice
public class ManejadorErrores {

	@ExceptionHandler(ReglaNegocioException.class)
	public ResponseEntity<Map<String, String>> reglaIncumplida(ReglaNegocioException ex) {
		return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> fallo(Exception ex) {
		String mensaje = ex.getMessage() == null ? "No se pudo completar la operacion" : ex.getMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", mensaje));
	}
}
