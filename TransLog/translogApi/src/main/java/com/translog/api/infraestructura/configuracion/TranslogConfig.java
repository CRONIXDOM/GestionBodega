package com.translog.api.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.translog.api.aplicacion.casosuso.entrada.ICiudadUseCase;
import com.translog.api.aplicacion.casosuso.entrada.IConductorUseCase;
import com.translog.api.aplicacion.casosuso.entrada.IConsultaUseCase;
import com.translog.api.aplicacion.casosuso.entrada.IDespachoUseCase;
import com.translog.api.aplicacion.casosuso.entrada.IEnvioUseCase;
import com.translog.api.aplicacion.casosuso.entrada.IRutaUseCase;
import com.translog.api.aplicacion.casosuso.entrada.IVehiculoUseCase;
import com.translog.api.aplicacion.casosuso.impl.CiudadUseCaseImpl;
import com.translog.api.aplicacion.casosuso.impl.ConductorUseCaseImpl;
import com.translog.api.aplicacion.casosuso.impl.ConsultaUseCaseImpl;
import com.translog.api.aplicacion.casosuso.impl.DespachoUseCaseImpl;
import com.translog.api.aplicacion.casosuso.impl.EnvioUseCaseImpl;
import com.translog.api.aplicacion.casosuso.impl.RutaUseCaseImpl;
import com.translog.api.aplicacion.casosuso.impl.VehiculoUseCaseImpl;
import com.translog.api.dominio.repositorio.ICiudadRepositorio;
import com.translog.api.dominio.repositorio.IConductorRepositorio;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;
import com.translog.api.dominio.repositorio.IEnvioRepositorio;
import com.translog.api.dominio.repositorio.IRutaRepositorio;
import com.translog.api.dominio.repositorio.IVehiculoRepositorio;
import com.translog.api.infraestructura.persistencia.adaptadores.CiudadRepositorioImpl;
import com.translog.api.infraestructura.persistencia.adaptadores.ConductorRepositorioImpl;
import com.translog.api.infraestructura.persistencia.adaptadores.DespachoRepositorioImpl;
import com.translog.api.infraestructura.persistencia.adaptadores.EnvioRepositorioImpl;
import com.translog.api.infraestructura.persistencia.adaptadores.RutaRepositorioImpl;
import com.translog.api.infraestructura.persistencia.adaptadores.VehiculoRepositorioImpl;
import com.translog.api.infraestructura.persistencia.mapeadores.ICiudadJpaMapper;
import com.translog.api.infraestructura.persistencia.mapeadores.IConductorJpaMapper;
import com.translog.api.infraestructura.persistencia.mapeadores.IDespachoJpaMapper;
import com.translog.api.infraestructura.persistencia.mapeadores.IEnvioJpaMapper;
import com.translog.api.infraestructura.persistencia.mapeadores.IRutaJpaMapper;
import com.translog.api.infraestructura.persistencia.mapeadores.IVehiculoJpaMapper;
import com.translog.api.infraestructura.repositorio.ICiudadJpaRepositorio;
import com.translog.api.infraestructura.repositorio.IConductorJpaRepositorio;
import com.translog.api.infraestructura.repositorio.IDespachoJpaRepositorio;
import com.translog.api.infraestructura.repositorio.IEnvioJpaRepositorio;
import com.translog.api.infraestructura.repositorio.IRutaJpaRepositorio;
import com.translog.api.infraestructura.repositorio.IVehiculoJpaRepositorio;

/**
 * Aquí se arma la aplicación: se le dice a Spring qué implementación usar para
 * cada puerto.
 *
 * Los casos de uso no llevan ninguna anotación de Spring: se construyen a mano,
 * pasándoles por el constructor los repositorios que necesitan. Por eso se
 * pueden probar sin levantar el framework, y por eso el dominio no sabe que
 * Spring existe.
 */
@Configuration
public class TranslogConfig {

	// ------------------------------------------------------------------ Ciudad

	@Bean
	ICiudadRepositorio ciudadRepositorio(ICiudadJpaRepositorio jpaRepositorio, ICiudadJpaMapper entityMapper) {
		return new CiudadRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	ICiudadUseCase ciudadUseCase(ICiudadRepositorio repositorio, IRutaRepositorio rutaRepositorio,
			IEnvioRepositorio envioRepositorio) {
		return new CiudadUseCaseImpl(repositorio, rutaRepositorio, envioRepositorio);
	}

	// -------------------------------------------------------------------- Ruta

	@Bean
	IRutaRepositorio rutaRepositorio(IRutaJpaRepositorio jpaRepositorio, IRutaJpaMapper entityMapper) {
		return new RutaRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IRutaUseCase rutaUseCase(IRutaRepositorio repositorio, ICiudadRepositorio ciudadRepositorio,
			IDespachoRepositorio despachoRepositorio) {
		return new RutaUseCaseImpl(repositorio, ciudadRepositorio, despachoRepositorio);
	}

	// ---------------------------------------------------------------- Vehículo

	@Bean
	IVehiculoRepositorio vehiculoRepositorio(IVehiculoJpaRepositorio jpaRepositorio,
			IVehiculoJpaMapper entityMapper) {
		return new VehiculoRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IVehiculoUseCase vehiculoUseCase(IVehiculoRepositorio repositorio, IDespachoRepositorio despachoRepositorio) {
		return new VehiculoUseCaseImpl(repositorio, despachoRepositorio);
	}

	// --------------------------------------------------------------- Conductor

	@Bean
	IConductorRepositorio conductorRepositorio(IConductorJpaRepositorio jpaRepositorio,
			IConductorJpaMapper entityMapper) {
		return new ConductorRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IConductorUseCase conductorUseCase(IConductorRepositorio repositorio,
			IDespachoRepositorio despachoRepositorio) {
		return new ConductorUseCaseImpl(repositorio, despachoRepositorio);
	}

	// ------------------------------------------------------------------- Envío

	@Bean
	IEnvioRepositorio envioRepositorio(IEnvioJpaRepositorio jpaRepositorio, IEnvioJpaMapper entityMapper) {
		return new EnvioRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IEnvioUseCase envioUseCase(IEnvioRepositorio repositorio, ICiudadRepositorio ciudadRepositorio) {
		return new EnvioUseCaseImpl(repositorio, ciudadRepositorio);
	}

	// ---------------------------------------------------------------- Despacho

	@Bean
	IDespachoRepositorio despachoRepositorio(IDespachoJpaRepositorio jpaRepositorio,
			IDespachoJpaMapper entityMapper) {
		return new DespachoRepositorioImpl(jpaRepositorio, entityMapper);
	}

	@Bean
	IDespachoUseCase despachoUseCase(IDespachoRepositorio repositorio, IRutaRepositorio rutaRepositorio,
			IVehiculoRepositorio vehiculoRepositorio, IConductorRepositorio conductorRepositorio,
			IEnvioRepositorio envioRepositorio) {
		return new DespachoUseCaseImpl(repositorio, rutaRepositorio, vehiculoRepositorio, conductorRepositorio,
				envioRepositorio);
	}

	// ---------------------------------------------------------------- Consulta

	@Bean
	IConsultaUseCase consultaUseCase(IDespachoRepositorio despachoRepositorio, IEnvioRepositorio envioRepositorio,
			IRutaRepositorio rutaRepositorio, IVehiculoRepositorio vehiculoRepositorio,
			IConductorRepositorio conductorRepositorio, ICiudadRepositorio ciudadRepositorio) {
		return new ConsultaUseCaseImpl(despachoRepositorio, envioRepositorio, rutaRepositorio, vehiculoRepositorio,
				conductorRepositorio, ciudadRepositorio);
	}

}
