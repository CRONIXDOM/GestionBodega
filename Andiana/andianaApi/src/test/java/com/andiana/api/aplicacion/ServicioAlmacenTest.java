package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.servicio.ServicioAlmacenProductoTerminado;
import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.modelo.AlmacenProductoTerminado;
import com.andiana.api.dominio.modelo.ControlCalidad;
import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.dominio.puerto.AlmacenProductoTerminadoRepositorio;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;

/** La regla central: al almacen solo entra lo que el laboratorio aprobo. */
class ServicioAlmacenTest {

	static class LotesEnMemoria extends RepositorioEnMemoria<LoteProduccion> implements LoteProduccionRepositorio {
		LotesEnMemoria() {
			super(LoteProduccion::getIdLote, LoteProduccion::setIdLote);
		}

		@Override
		public List<LoteProduccion> buscarPorOrden(Integer idOrden) {
			return listar().stream().filter(l -> idOrden.equals(l.getIdOrden())).toList();
		}
	}

	static class ControlesEnMemoria extends RepositorioEnMemoria<ControlCalidad> implements ControlCalidadRepositorio {
		ControlesEnMemoria() {
			super(ControlCalidad::getIdControl, ControlCalidad::setIdControl);
		}

		@Override
		public Optional<ControlCalidad> buscarPorLote(Integer idLote) {
			return listar().stream().filter(c -> idLote.equals(c.getIdLote())).findFirst();
		}
	}

	static class AlmacenEnMemoria extends RepositorioEnMemoria<AlmacenProductoTerminado>
			implements AlmacenProductoTerminadoRepositorio {
		AlmacenEnMemoria() {
			super(AlmacenProductoTerminado::getIdAlmacen, AlmacenProductoTerminado::setIdAlmacen);
		}

		@Override
		public Optional<AlmacenProductoTerminado> buscarPorLote(Integer idLote) {
			return listar().stream().filter(a -> idLote.equals(a.getIdLote())).findFirst();
		}
	}

	private LotesEnMemoria lotes;
	private ControlesEnMemoria controles;
	private AlmacenEnMemoria almacen;
	private ServicioAlmacenProductoTerminado servicio;
	private Integer idLote;

	@BeforeEach
	void prepararProduccion() {
		lotes = new LotesEnMemoria();
		controles = new ControlesEnMemoria();
		almacen = new AlmacenEnMemoria();
		servicio = new ServicioAlmacenProductoTerminado(almacen, lotes, controles);

		idLote = lotes.guardar(new LoteProduccion(null, "LT-0001", 1, 500, LocalDate.now())).getIdLote();
	}

	private void inspeccionar(String resultado) {
		controles.guardar(new ControlCalidad(null, idLote, new BigDecimal("3.0"), new BigDecimal("10.0"),
				new BigDecimal("4.0"), resultado, LocalDate.now()));
	}

	private AlmacenProductoTerminado guardar(int cantidad) {
		return servicio.guardar(new AlmacenProductoTerminado(null, idLote, cantidad, "PASILLO A", LocalDate.now()));
	}

	@Test
	void unLoteAprobadoEntraAlAlmacen() {
		inspeccionar("APROBADO");

		assertThat(guardar(500).getIdAlmacen()).isNotNull();
	}

	@Test
	void unLoteSinControlDeCalidadNoEntra() {
		assertThatThrownBy(() -> guardar(500))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("todavia no tiene control de calidad");
	}

	@Test
	void unLoteRechazadoNoEntra() {
		inspeccionar("RECHAZADO");

		assertThatThrownBy(() -> guardar(500))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("solo entran al almacen los lotes aprobados");
	}

	@Test
	void unLoteObservadoTampocoEntra() {
		inspeccionar("OBSERVADO");

		assertThatThrownBy(() -> guardar(500))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("solo entran al almacen los lotes aprobados");
	}

	@Test
	void noSePuedeGuardarMasDeLoQueElLoteProdujo() {
		inspeccionar("APROBADO");

		assertThatThrownBy(() -> guardar(900))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("el lote LT-0001 produjo 500");
	}

	@Test
	void elMismoLoteNoSeGuardaDosVeces() {
		inspeccionar("APROBADO");
		guardar(500);

		assertThatThrownBy(() -> guardar(500))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("ya esta guardado en el almacen");
	}

	@Test
	void laUbicacionSeGuardaNormalizada() {
		inspeccionar("APROBADO");

		AlmacenProductoTerminado registro = servicio.guardar(new AlmacenProductoTerminado(
				null, idLote, 500, "  pasillo   a - estante 1 ", LocalDate.now()));

		assertThat(registro.getUbicacionFisica()).isEqualTo("PASILLO A - ESTANTE 1");
	}
}
