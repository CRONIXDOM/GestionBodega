package com.andiana.api.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.andiana.api.aplicacion.casosuso.entrada.IConsultaUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IDetalleRecetaUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IMateriaPrimaUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IProductoUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IRecetaProduccionUseCase;
import com.andiana.api.aplicacion.casosuso.impl.ConsultaUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.DetalleRecetaUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.MateriaPrimaUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.ProductoUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.RecetaProduccionUseCaseImpl;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;
import com.andiana.api.infraestructura.persistencia.adaptadores.DetalleRecetaRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.MateriaPrimaRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.ProductoRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.RecetaProduccionRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.mapeadores.IDetalleRecetaJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IMateriaPrimaJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IProductoJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IRecetaProduccionJpaMapper;
import com.andiana.api.infraestructura.repositorio.IDetalleRecetaJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IMateriaPrimaJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IProductoJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IRecetaProduccionJpaRepositorio;

/**
 * Aqui se arma la aplicacion: se le dice a Spring que implementacion usar para
 * cada puerto.
 *
 * Los casos de uso no llevan ninguna anotacion de Spring: se construyen a mano,
 * pasandoles por el constructor los repositorios que necesitan. Por eso pueden
 * probarse sin levantar el framework, y por eso el dominio no sabe que existe
 * Spring.
 */
@Configuration
public class AndianaConfig {

	// ---------------------------------------------------------------- Producto

	@Bean
	IProductoRepositorio productoRepositorio(IProductoJpaRepositorio jpaRepositorio, IProductoJpaMapper entityMapper) {
		return new ProductoRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IProductoUseCase productoUseCase(IProductoRepositorio repositorio, IRecetaProduccionRepositorio recetaRepositorio) {
		return new ProductoUseCaseImpl(repositorio, recetaRepositorio);
	}

	// ----------------------------------------------------------- Materia prima

	@Bean
	IMateriaPrimaRepositorio materiaPrimaRepositorio(IMateriaPrimaJpaRepositorio jpaRepositorio,
			IMateriaPrimaJpaMapper entityMapper) {
		return new MateriaPrimaRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IMateriaPrimaUseCase materiaPrimaUseCase(IMateriaPrimaRepositorio repositorio) {
		return new MateriaPrimaUseCaseImpl(repositorio);
	}

	// ------------------------------------------------------------------ Receta

	@Bean
	IRecetaProduccionRepositorio recetaProduccionRepositorio(IRecetaProduccionJpaRepositorio jpaRepositorio,
			IRecetaProduccionJpaMapper entityMapper) {
		return new RecetaProduccionRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IRecetaProduccionUseCase recetaProduccionUseCase(IRecetaProduccionRepositorio repositorio,
			IProductoRepositorio productoRepositorio, IDetalleRecetaRepositorio detalleRepositorio) {
		return new RecetaProduccionUseCaseImpl(repositorio, productoRepositorio, detalleRepositorio);
	}

	// ---------------------------------------------------------- Detalle receta

	@Bean
	IDetalleRecetaRepositorio detalleRecetaRepositorio(IDetalleRecetaJpaRepositorio jpaRepositorio,
			IDetalleRecetaJpaMapper entityMapper) {
		return new DetalleRecetaRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IDetalleRecetaUseCase detalleRecetaUseCase(IDetalleRecetaRepositorio repositorio,
			IRecetaProduccionRepositorio recetaRepositorio, IMateriaPrimaRepositorio materiaRepositorio) {
		return new DetalleRecetaUseCaseImpl(repositorio, recetaRepositorio, materiaRepositorio);
	}

	// --------------------------------------------------------------- Consultas

	@Bean
	IConsultaUseCase consultaUseCase(IRecetaProduccionRepositorio recetaRepositorio,
			IDetalleRecetaRepositorio detalleRepositorio, IMateriaPrimaRepositorio materiaRepositorio,
			IProductoRepositorio productoRepositorio) {
		return new ConsultaUseCaseImpl(recetaRepositorio, detalleRepositorio, materiaRepositorio, productoRepositorio);
	}

}
