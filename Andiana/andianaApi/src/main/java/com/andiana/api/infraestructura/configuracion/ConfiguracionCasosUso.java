package com.andiana.api.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.andiana.api.aplicacion.puerto.ConsultaRecetas;
import com.andiana.api.aplicacion.servicio.ServicioAlmacenProductoTerminado;
import com.andiana.api.aplicacion.servicio.ServicioConsultaRecetas;
import com.andiana.api.aplicacion.servicio.ServicioControlCalidad;
import com.andiana.api.aplicacion.servicio.ServicioLoteProduccion;
import com.andiana.api.aplicacion.servicio.ServicioMateriaPrima;
import com.andiana.api.aplicacion.servicio.ServicioMovimientoInventario;
import com.andiana.api.aplicacion.servicio.ServicioOrdenProduccion;
import com.andiana.api.aplicacion.servicio.ServicioProducto;
import com.andiana.api.aplicacion.servicio.ServicioReceta;
import com.andiana.api.aplicacion.servicio.ServicioRecetaDetalle;
import com.andiana.api.dominio.puerto.AlmacenProductoTerminadoRepositorio;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.MovimientoInventarioRepositorio;
import com.andiana.api.dominio.puerto.OrdenProduccionRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaDetalleRepositorio;
import com.andiana.api.dominio.puerto.RecetaRepositorio;

/**
 * Aqui, y solo aqui, se une Spring con los casos de uso. Los servicios de la
 * capa de aplicacion no llevan ninguna anotacion: se construyen a mano pasandoles
 * los repositorios. Ese es el motivo de que se puedan probar sin levantar la
 * aplicacion y de que cambiar de framework no obligue a tocarlos.
 */
@Configuration
public class ConfiguracionCasosUso {

	@Bean
	ServicioProducto servicioProducto(ProductoRepositorio productos) {
		return new ServicioProducto(productos);
	}

	@Bean
	ServicioMateriaPrima servicioMateriaPrima(MateriaPrimaRepositorio materias) {
		return new ServicioMateriaPrima(materias);
	}

	@Bean
	ServicioReceta servicioReceta(RecetaRepositorio recetas, ProductoRepositorio productos,
			RecetaDetalleRepositorio detalles) {
		return new ServicioReceta(recetas, productos, detalles);
	}

	@Bean
	ServicioRecetaDetalle servicioRecetaDetalle(RecetaDetalleRepositorio detalles, RecetaRepositorio recetas,
			MateriaPrimaRepositorio materias) {
		return new ServicioRecetaDetalle(detalles, recetas, materias);
	}

	@Bean
	ServicioMovimientoInventario servicioMovimientoInventario(MovimientoInventarioRepositorio movimientos,
			MateriaPrimaRepositorio materias) {
		return new ServicioMovimientoInventario(movimientos, materias);
	}

	@Bean
	ServicioOrdenProduccion servicioOrdenProduccion(OrdenProduccionRepositorio ordenes,
			ProductoRepositorio productos, LoteProduccionRepositorio lotes) {
		return new ServicioOrdenProduccion(ordenes, productos, lotes);
	}

	@Bean
	ServicioLoteProduccion servicioLoteProduccion(LoteProduccionRepositorio lotes,
			OrdenProduccionRepositorio ordenes, ControlCalidadRepositorio controles,
			AlmacenProductoTerminadoRepositorio almacen) {
		return new ServicioLoteProduccion(lotes, ordenes, controles, almacen);
	}

	@Bean
	ServicioControlCalidad servicioControlCalidad(ControlCalidadRepositorio controles,
			LoteProduccionRepositorio lotes, AlmacenProductoTerminadoRepositorio almacen) {
		return new ServicioControlCalidad(controles, lotes, almacen);
	}

	@Bean
	ServicioAlmacenProductoTerminado servicioAlmacen(AlmacenProductoTerminadoRepositorio almacen,
			LoteProduccionRepositorio lotes, ControlCalidadRepositorio controles) {
		return new ServicioAlmacenProductoTerminado(almacen, lotes, controles);
	}

	@Bean
	ConsultaRecetas consultaRecetas(RecetaRepositorio recetas, RecetaDetalleRepositorio detalles,
			MateriaPrimaRepositorio materias, ProductoRepositorio productos) {
		return new ServicioConsultaRecetas(recetas, detalles, materias, productos);
	}
}
