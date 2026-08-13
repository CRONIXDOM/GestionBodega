package com.andiana.api.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.andiana.api.aplicacion.casosuso.entrada.IProductoUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IMateriaPrimaUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IRecetaProduccionUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IDetalleRecetaUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IMovimientoMateriaPrimaUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IOrdenProduccionUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.ILoteProduccionUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IControlCalidadUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IInventarioProductoUseCase;
import com.andiana.api.aplicacion.casosuso.entrada.IConsultaUseCase;
import com.andiana.api.aplicacion.casosuso.impl.ProductoUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.MateriaPrimaUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.RecetaProduccionUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.DetalleRecetaUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.MovimientoMateriaPrimaUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.OrdenProduccionUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.LoteProduccionUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.ControlCalidadUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.InventarioProductoUseCaseImpl;
import com.andiana.api.aplicacion.casosuso.impl.ConsultaUseCaseImpl;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.dominio.repositorio.IMovimientoMateriaPrimaRepositorio;
import com.andiana.api.dominio.repositorio.IOrdenProduccionRepositorio;
import com.andiana.api.dominio.repositorio.ILoteProduccionRepositorio;
import com.andiana.api.dominio.repositorio.IControlCalidadRepositorio;
import com.andiana.api.dominio.repositorio.IInventarioProductoRepositorio;
import com.andiana.api.infraestructura.persistencia.adaptadores.ProductoRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.MateriaPrimaRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.RecetaProduccionRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.DetalleRecetaRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.MovimientoMateriaPrimaRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.OrdenProduccionRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.LoteProduccionRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.ControlCalidadRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.adaptadores.InventarioProductoRepositorioImpl;
import com.andiana.api.infraestructura.persistencia.mapeadores.IProductoJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IMateriaPrimaJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IRecetaProduccionJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IDetalleRecetaJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IMovimientoMateriaPrimaJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IOrdenProduccionJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.ILoteProduccionJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IControlCalidadJpaMapper;
import com.andiana.api.infraestructura.persistencia.mapeadores.IInventarioProductoJpaMapper;
import com.andiana.api.infraestructura.repositorio.IProductoJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IMateriaPrimaJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IRecetaProduccionJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IDetalleRecetaJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IMovimientoMateriaPrimaJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IOrdenProduccionJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.ILoteProduccionJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IControlCalidadJpaRepositorio;
import com.andiana.api.infraestructura.repositorio.IInventarioProductoJpaRepositorio;

@Configuration
public class AndianaConfig {

	@Bean
	IProductoRepositorio productoRepositorio(IProductoJpaRepositorio jpaRepositorio, IProductoJpaMapper entityMapper) {
		return new ProductoRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IMateriaPrimaRepositorio materiaPrimaRepositorio(IMateriaPrimaJpaRepositorio jpaRepositorio,
			IMateriaPrimaJpaMapper entityMapper) {
		return new MateriaPrimaRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IRecetaProduccionRepositorio recetaProduccionRepositorio(IRecetaProduccionJpaRepositorio jpaRepositorio,
			IRecetaProduccionJpaMapper entityMapper) {
		return new RecetaProduccionRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IDetalleRecetaRepositorio detalleRecetaRepositorio(IDetalleRecetaJpaRepositorio jpaRepositorio,
			IDetalleRecetaJpaMapper entityMapper) {
		return new DetalleRecetaRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IMovimientoMateriaPrimaRepositorio movimientoMateriaPrimaRepositorio(
			IMovimientoMateriaPrimaJpaRepositorio jpaRepositorio, IMovimientoMateriaPrimaJpaMapper entityMapper) {
		return new MovimientoMateriaPrimaRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IOrdenProduccionRepositorio ordenProduccionRepositorio(IOrdenProduccionJpaRepositorio jpaRepositorio,
			IOrdenProduccionJpaMapper entityMapper) {
		return new OrdenProduccionRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	ILoteProduccionRepositorio loteProduccionRepositorio(ILoteProduccionJpaRepositorio jpaRepositorio,
			ILoteProduccionJpaMapper entityMapper) {
		return new LoteProduccionRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IControlCalidadRepositorio controlCalidadRepositorio(IControlCalidadJpaRepositorio jpaRepositorio,
			IControlCalidadJpaMapper entityMapper) {
		return new ControlCalidadRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IInventarioProductoRepositorio inventarioProductoRepositorio(IInventarioProductoJpaRepositorio jpaRepositorio,
			IInventarioProductoJpaMapper entityMapper) {
		return new InventarioProductoRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IProductoUseCase productoUseCase(IProductoRepositorio productoRepositorio,
			IRecetaProduccionRepositorio recetaProduccionRepositorio) {
		return new ProductoUseCaseImpl(productoRepositorio, recetaProduccionRepositorio);
	}

	@Bean
	IMateriaPrimaUseCase materiaPrimaUseCase(IMateriaPrimaRepositorio materiaPrimaRepositorio) {
		return new MateriaPrimaUseCaseImpl(materiaPrimaRepositorio);
	}

	@Bean
	IRecetaProduccionUseCase recetaProduccionUseCase(IRecetaProduccionRepositorio recetaProduccionRepositorio,
			IProductoRepositorio productoRepositorio, IDetalleRecetaRepositorio detalleRecetaRepositorio) {
		return new RecetaProduccionUseCaseImpl(recetaProduccionRepositorio, productoRepositorio,
				detalleRecetaRepositorio);
	}

	@Bean
	IDetalleRecetaUseCase detalleRecetaUseCase(IDetalleRecetaRepositorio detalleRecetaRepositorio,
			IRecetaProduccionRepositorio recetaProduccionRepositorio,
			IMateriaPrimaRepositorio materiaPrimaRepositorio) {
		return new DetalleRecetaUseCaseImpl(detalleRecetaRepositorio, recetaProduccionRepositorio,
				materiaPrimaRepositorio);
	}

	@Bean
	IMovimientoMateriaPrimaUseCase movimientoMateriaPrimaUseCase(
			IMovimientoMateriaPrimaRepositorio movimientoMateriaPrimaRepositorio,
			IMateriaPrimaRepositorio materiaPrimaRepositorio) {
		return new MovimientoMateriaPrimaUseCaseImpl(movimientoMateriaPrimaRepositorio, materiaPrimaRepositorio);
	}

	@Bean
	IOrdenProduccionUseCase ordenProduccionUseCase(IOrdenProduccionRepositorio ordenProduccionRepositorio,
			IProductoRepositorio productoRepositorio, ILoteProduccionRepositorio loteProduccionRepositorio) {
		return new OrdenProduccionUseCaseImpl(ordenProduccionRepositorio, productoRepositorio,
				loteProduccionRepositorio);
	}

	@Bean
	ILoteProduccionUseCase loteProduccionUseCase(ILoteProduccionRepositorio loteProduccionRepositorio,
			IOrdenProduccionRepositorio ordenProduccionRepositorio,
			IControlCalidadRepositorio controlCalidadRepositorio,
			IInventarioProductoRepositorio inventarioProductoRepositorio) {
		return new LoteProduccionUseCaseImpl(loteProduccionRepositorio, ordenProduccionRepositorio,
				controlCalidadRepositorio, inventarioProductoRepositorio);
	}

	@Bean
	IControlCalidadUseCase controlCalidadUseCase(IControlCalidadRepositorio controlCalidadRepositorio,
			ILoteProduccionRepositorio loteProduccionRepositorio,
			IInventarioProductoRepositorio inventarioProductoRepositorio) {
		return new ControlCalidadUseCaseImpl(controlCalidadRepositorio, loteProduccionRepositorio,
				inventarioProductoRepositorio);
	}

	@Bean
	IInventarioProductoUseCase inventarioProductoUseCase(IInventarioProductoRepositorio inventarioProductoRepositorio,
			ILoteProduccionRepositorio loteProduccionRepositorio,
			IControlCalidadRepositorio controlCalidadRepositorio) {
		return new InventarioProductoUseCaseImpl(inventarioProductoRepositorio, loteProduccionRepositorio,
				controlCalidadRepositorio);
	}

	@Bean
	IConsultaUseCase consultaUseCase(IRecetaProduccionRepositorio recetaProduccionRepositorio,
			IDetalleRecetaRepositorio detalleRecetaRepositorio, IMateriaPrimaRepositorio materiaPrimaRepositorio,
			IProductoRepositorio productoRepositorio) {
		return new ConsultaUseCaseImpl(recetaProduccionRepositorio, detalleRecetaRepositorio, materiaPrimaRepositorio,
				productoRepositorio);
	}

}
