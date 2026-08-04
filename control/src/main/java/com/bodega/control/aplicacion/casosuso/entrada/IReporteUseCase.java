package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Reporte;

public interface IReporteUseCase {
	
	Reporte guardar (Reporte nuevaReporte);
	
	Reporte buscarPorid (int Reporte);
	
	List<Reporte> listarTodos();
	
	void eliminar (int Reporte);

	Reporte buscarPorId(int idReporte);
	

}
