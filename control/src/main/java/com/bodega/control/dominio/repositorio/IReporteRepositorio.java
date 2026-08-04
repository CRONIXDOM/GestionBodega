package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Reporte;

public interface IReporteRepositorio {
	
	Reporte guardar (Reporte nuevaReporte);
	
	Optional<Reporte> buscarPorid (int Reporte);
	
	List<Reporte> listarTodos();
	
	void eliminar (int Reporte);
	
}
