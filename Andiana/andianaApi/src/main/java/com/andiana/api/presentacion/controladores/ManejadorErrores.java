package com.andiana.api.presentacion.controladores;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorErrores {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> reglaIncumplida(RuntimeException ex) {

		String mensaje = ex.getMessage() == null ? "No se pudo completar la operación" : ex.getMessage();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", mensaje));
	}

}
