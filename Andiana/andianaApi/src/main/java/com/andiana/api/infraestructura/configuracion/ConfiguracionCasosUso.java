package com.andiana.api.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.andiana.api.aplicacion.puerto.ConsultaRecetas;
import com.andiana.api.aplicacion.servicio.ServicioConsultaRecetas;
import com.andiana.api.aplicacion.servicio.ServicioControlCalidad;
import com.andiana.api.aplicacion.servicio.ServicioDetalleReceta;
import com.andiana.api.aplicacion.servicio.ServicioInventarioProducto;
import com.andiana.api.aplicacion.servicio.ServicioLoteProduccion;
import com.andiana.api.aplicacion.servicio.ServicioMateriaPrima;
import com.andiana.api.aplicacion.servicio.ServicioMovimientoMateriaPrima;
import com.andiana.api.aplicacion.servicio.ServicioOrdenProduccion;
import com.andiana.api.aplicacion.servicio.ServicioProducto;
import com.andiana.api.aplicacion.servicio.ServicioRecetaProduccion;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.DetalleRecetaRepositorio;
import com.andiana.api.dominio.puerto.InventarioProductoRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.MovimientoMateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.OrdenProduccionRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaProduccionRepositorio;

/**
 * Aqui, y solo aqui, se une Spring con los casos de uso. Los servicios de la
 * capa de aplicacion no llevan ninguna anotacion: se construyen a mano pasandoles
 * los repositorios. Ese es el motivo de que se puedan probar sin levantar la
 * aplicacion y de que cambiar de framework no obligue a tocarlos.
 */
@Configuration
public class ConfiguracionCasosUso {

	@Bean
	ServicioProducto servicioProducto(ProductoRepositorio productos, RecetaProduccionRepositorio recetas) {
		return new ServicioProducto(productos, recetas);
	}

	@Bean
	ServicioMateriaPrima servicioMateriaPrima(MateriaPrimaRepositorio materias) {
		return new ServicioMateriaPrima(materias);
	}

	@Bean
	ServicioRecetaProduccion servicioReceta(RecetaProduccionRepositorio recetas, ProductoRepositorio productos,
			DetalleRecetaRepositorio detalles) {
		return new ServicioRecetaProduccion(recetas, productos, detalles);
	}

	@Bean
	ServicioDetalleReceta servicioDetalleReceta(DetalleRecetaRepositorio detalles,
			RecetaProduccionRepositorio recetas, MateriaPrimaRepositorio materias) {
		return new ServicioDetalleReceta(detalles, recetas, materias);
	}

	@Bean
	ServicioMovimientoMateriaPrima servicioMovimiento(MovimientoMateriaPrimaRepositorio movimientos,
			MateriaPrimaRepositorio materias) {
		return new ServicioMovimientoMateriaPrima(movimientos, materias);
	}

	@Bean
	ServicioOrdenProduccion servicioOrden(OrdenProduccionRepositorio ordenes, ProductoRepositorio productos,
			LoteProduccionRepositorio lotes) {
		return new ServicioOrdenProduccion(ordenes, productos, lotes);
	}

	@Bean
	ServicioLoteProduccion servicioLote(LoteProduccionRepositorio lotes, OrdenProduccionRepositorio ordenes,
			ControlCalidadRepositorio controles, InventarioProductoRepositorio inventario) {
		return new ServicioLoteProduccion(lotes, ordenes, controles, inventario);
	}

	@Bean
	ServicioControlCalidad servicioControlCalidad(ControlCalidadRepositorio controles,
			LoteProduccionRepositorio lotes, InventarioProductoRepositorio inventario) {
		return new ServicioControlCalidad(controles, lotes, inventario);
	}

	@Bean
	ServicioInventarioProducto servicioInventario(InventarioProductoRepositorio inventario,
			LoteProduccionRepositorio lotes, ControlCalidadRepositorio controles,
			ServicioControlCalidad servicioControl) {
		return new ServicioInventarioProducto(inventario, lotes, controles, servicioControl);
	}

	@Bean
	ConsultaRecetas consultaRecetas(RecetaProduccionRepositorio recetas, DetalleRecetaRepositorio detalles,
			MateriaPrimaRepositorio materias, ProductoRepositorio productos) {
		return new ServicioConsultaRecetas(recetas, detalles, materias, productos);
	}
}
