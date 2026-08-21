package com.translog.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.dominio.entidades.Envio;

public interface IDespachoUseCase {

	/**
	 * Registra el despacho junto con los envíos que va a transportar. Es una sola
	 * operación porque la regla de negocio habla de las dos cosas a la vez: no se
	 * puede validar el peso ni el origen y destino sin saber qué envíos lleva.
	 */
	Despacho guardar(Despacho nuevoDespacho, List<Integer> idsDeEnvios);

	Despacho buscarPorId(int idDespacho);

	List<Despacho> listarTodos();

	void eliminar(int idDespacho);

	/** Los envíos que transporta un despacho. */
	List<Envio> enviosDelDespacho(int idDespacho);

	/**
	 * Los envíos que hoy se podrían cargar en una ruta: los que van de su origen
	 * a su destino y todavía no están en ningún despacho.
	 */
	List<Envio> enviosDisponiblesParaLaRuta(int idRuta, Integer idDespachoQueSeEdita);

}
