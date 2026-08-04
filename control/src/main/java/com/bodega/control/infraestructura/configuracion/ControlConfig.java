package com.bodega.control.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bodega.control.aplicacion.casosuso.entrada.ICredencialesUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IDetalleEntregaUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IDetalleSolicitudUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IEntregaUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.ILoteUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IProductoUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IRegistroUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IReporteUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IRolUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.ISolicitudUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.ITipoUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IUbicacionUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioRolUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.ISedeUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IZonaUseCase;
import com.bodega.control.aplicacion.casosuso.impl.CredencialesUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.DetalleEntregaUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.DetalleSolicitudUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.EntregaUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.LoteUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.ProductoUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.RegistroUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.ReporteUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.RolUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.SolicitudUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.TipoUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.UbicacionUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.UsuarioRolUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.UsuarioUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.SedeUseCaseImpl;
import com.bodega.control.aplicacion.casosuso.impl.ZonaUseCaseImpl;
import com.bodega.control.dominio.repositorio.ICredencialesRepositorio;
import com.bodega.control.dominio.repositorio.IDetalleEntregaRepositorio;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudLoteRepositorio;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudRepositorio;
import com.bodega.control.dominio.repositorio.IEntregaRepositorio;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;
import com.bodega.control.dominio.repositorio.IProductoRepositorio;
import com.bodega.control.dominio.repositorio.IRegistroRepositorio;
import com.bodega.control.dominio.repositorio.IReporteRepositorio;
import com.bodega.control.dominio.repositorio.IRolRepositorio;
import com.bodega.control.dominio.repositorio.ISolicitudRepositorio;
import com.bodega.control.dominio.repositorio.ITipoRepositorio;
import com.bodega.control.dominio.repositorio.IUbicacionRepositorio;
import com.bodega.control.dominio.repositorio.IUsuarioRepositorio;
import com.bodega.control.dominio.repositorio.IUsuarioRolRepositorio;
import com.bodega.control.dominio.repositorio.ISedeRepositorio;
import com.bodega.control.dominio.repositorio.IZonaRepositorio;
import com.bodega.control.infraestructura.persistencia.adaptadores.CredencialesRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.DetalleEntregaRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.DetalleSolicitudLoteRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.DetalleSolicitudRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.EntregaRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.LoteRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.ProductoRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.RegistroRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.ReporteRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.RolRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.SolicitudRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.TipoRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.UbicacionRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.UsuarioRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.UsuarioRolRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.SedeRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.adaptadores.ZonaRepositorioImpl;
import com.bodega.control.infraestructura.persistencia.mapeadores.ICredencialesJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IDetalleEntregaJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IDetalleSolicitudJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IDetalleSolicitudLoteJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IEntregaJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.ILoteJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IProductoJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IRegistroJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IReporteJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IRolJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.ISolicitudJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.ITipoJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IUbicacionJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IUsuarioJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IUsuarioRolJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.ISedeJpaMapper;
import com.bodega.control.infraestructura.persistencia.mapeadores.IZonaJpaMapper;
import com.bodega.control.infraestructura.repositorio.ICredencialesJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IDetalleEntregaJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IDetalleSolicitudJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IDetalleSolicitudLoteJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IEntregaJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.ILoteJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IProductoJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IRegistroJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IReporteJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IRolJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.ISolicitudJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.ITipoJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IUbicacionJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IUsuarioJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IUsuarioRolJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.ISedeJpaRepositorio;
import com.bodega.control.infraestructura.repositorio.IZonaJpaRepositorio;

@Configuration
public class ControlConfig {

	// credenciales ****
	@Bean
	ICredencialesRepositorio credencialesRepositorio(ICredencialesJpaRepositorio jpaRepositorio,
			ICredencialesJpaMapper mapper) {
		return new CredencialesRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	ICredencialesUseCase credencialesUseCase(ICredencialesRepositorio repositorio) {
		return new CredencialesUseCaseImpl(repositorio);
	}

	// detalleEntrega ****
	@Bean
	IDetalleEntregaRepositorio detalleEntregaRepositorio(IDetalleEntregaJpaRepositorio jpaRepositorio,
			IDetalleEntregaJpaMapper mapper) {
		return new DetalleEntregaRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IDetalleEntregaUseCase detalleEntregaUseCase(IDetalleEntregaRepositorio repositorio, ILoteRepositorio loteRepositorio,
			IDetalleSolicitudLoteRepositorio asignacionRepositorio) {
		return new DetalleEntregaUseCaseImpl(repositorio, loteRepositorio, asignacionRepositorio);
	}

	// detalleSolicitud ****
	@Bean
	IDetalleSolicitudRepositorio detalleSolicitudRepositorio(IDetalleSolicitudJpaRepositorio jpaRepositorio,
			IDetalleSolicitudJpaMapper mapper) {
		return new DetalleSolicitudRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IDetalleSolicitudUseCase detalleSolicitudUseCase(IDetalleSolicitudRepositorio repositorio,
			ILoteRepositorio loteRepositorio, IDetalleSolicitudLoteRepositorio asignacionRepositorio) {
		return new DetalleSolicitudUseCaseImpl(repositorio, loteRepositorio, asignacionRepositorio);
	}

	// detalleSolicitudLote (reserva de stock por FIFO) ****
	@Bean
	IDetalleSolicitudLoteRepositorio detalleSolicitudLoteRepositorio(IDetalleSolicitudLoteJpaRepositorio jpaRepositorio,
			IDetalleSolicitudLoteJpaMapper mapper) {
		return new DetalleSolicitudLoteRepositorioImpl(jpaRepositorio, mapper);
	}

	// entrega ****
	@Bean
	IEntregaRepositorio entregaRepositorio(IEntregaJpaRepositorio jpaRepositorio, IEntregaJpaMapper mapper) {
		return new EntregaRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IEntregaUseCase entregaUseCase(IEntregaRepositorio repositorio) {
		return new EntregaUseCaseImpl(repositorio);
	}

	// lote ****
	@Bean
	ILoteRepositorio loteRepositorio(ILoteJpaRepositorio jpaRepositorio, ILoteJpaMapper mapper) {
		return new LoteRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	ILoteUseCase loteUseCase(ILoteRepositorio repositorio) {
		return new LoteUseCaseImpl(repositorio);
	}

	// producto ****
	@Bean
	IProductoRepositorio productoSolicitudRepositorio(IProductoJpaRepositorio jpaRepositorio, IProductoJpaMapper mapper) {
		return new ProductoRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IProductoUseCase productoUseCase(IProductoRepositorio repositorio) {
		return new ProductoUseCaseImpl(repositorio);
	}

	// registro ****
	@Bean
	IRegistroRepositorio registroRepositorio(IRegistroJpaRepositorio jpaRepositorio,
			IRegistroJpaMapper mapper) {
		return new RegistroRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IRegistroUseCase registroUseCase(IRegistroRepositorio repositorio) {
		return new RegistroUseCaseImpl(repositorio);
	}

	// reporte ****
	@Bean
	IReporteRepositorio reporteRepositorio(IReporteJpaRepositorio jpaRepositorio, IReporteJpaMapper mapper) {
		return new ReporteRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IReporteUseCase reporteUseCase(IReporteRepositorio repositorio) {
		return new ReporteUseCaseImpl(repositorio);
	}

	// rol ****
	@Bean
	IRolRepositorio rolRepositorio(IRolJpaRepositorio jpaRepositorio, IRolJpaMapper mapper) {
		return new RolRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IRolUseCase rolUseCase(IRolRepositorio repositorio) {
		return new RolUseCaseImpl(repositorio);
	}

	// solicitud ****
	@Bean
	ISolicitudRepositorio solicitudRepositorio(ISolicitudJpaRepositorio jpaRepositorio,
			ISolicitudJpaMapper mapper) {
		return new SolicitudRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	ISolicitudUseCase solicitudUseCase(ISolicitudRepositorio repositorio) {
		return new SolicitudUseCaseImpl(repositorio);
	}

	// tipo ****
	@Bean
	ITipoRepositorio tipoRepositorio(ITipoJpaRepositorio jpaRepositorio, ITipoJpaMapper mapper) {
		return new TipoRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	ITipoUseCase tipoUseCase(ITipoRepositorio repositorio) {
		return new TipoUseCaseImpl(repositorio);
	}

	// ubicacion ****
	@Bean
	IUbicacionRepositorio ubicacionRepositorio(IUbicacionJpaRepositorio jpaRepositorio, IUbicacionJpaMapper mapper) {
		return new UbicacionRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IUbicacionUseCase ubicacionUseCase(IUbicacionRepositorio repositorio) {
		return new UbicacionUseCaseImpl(repositorio);
	}

	// usuario ****
	@Bean
	IUsuarioRepositorio usuarioRepositorio(IUsuarioJpaRepositorio jpaRepositorio, IUsuarioJpaMapper mapper) {
		return new UsuarioRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IUsuarioUseCase usuarioUseCase(IUsuarioRepositorio repositorio) {
		return new UsuarioUseCaseImpl(repositorio);
	}

	// usuarioRol ****
	@Bean
	IUsuarioRolRepositorio usuarioRolRepositorio(IUsuarioRolJpaRepositorio jpaRepositorio, IUsuarioRolJpaMapper mapper) {
		return new UsuarioRolRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IUsuarioRolUseCase usuarioRolUseCase(IUsuarioRolRepositorio repositorio) {
		return new UsuarioRolUseCaseImpl(repositorio);
	}

	// zona ****
	@Bean
	IZonaRepositorio zonaRepositorio(IZonaJpaRepositorio jpaRepositorio, IZonaJpaMapper mapper) {
		return new ZonaRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	IZonaUseCase zonaUseCase(IZonaRepositorio repositorio) {
		return new ZonaUseCaseImpl(repositorio);
	}

	// sede ****
	@Bean
	ISedeRepositorio sedeRepositorio(ISedeJpaRepositorio jpaRepositorio, ISedeJpaMapper mapper) {
		return new SedeRepositorioImpl(jpaRepositorio, mapper);
	}

	@Bean
	ISedeUseCase sedeUseCase(ISedeRepositorio repositorio) {
		return new SedeUseCaseImpl(repositorio);
	}
}
