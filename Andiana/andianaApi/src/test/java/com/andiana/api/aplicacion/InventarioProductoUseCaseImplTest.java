package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.casosuso.impl.InventarioProductoUseCaseImpl;
import com.andiana.api.dominio.entidades.ControlCalidad;
import com.andiana.api.dominio.entidades.InventarioProducto;
import com.andiana.api.dominio.entidades.LoteProduccion;
import com.andiana.api.dominio.repositorio.IControlCalidadRepositorio;
import com.andiana.api.dominio.repositorio.IInventarioProductoRepositorio;
import com.andiana.api.dominio.repositorio.ILoteProduccionRepositorio;

/** La regla central: al inventario solo entra lo que el laboratorio aprobo. */
class InventarioProductoUseCaseImplTest {

	static class LotesEnMemoria extends RepositorioEnMemoria<LoteProduccion> implements ILoteProduccionRepositorio {
		LotesEnMemoria() {
			super(LoteProduccion::getIdLote, LoteProduccion::setIdLote);
		}

		@Override
		protected LoteProduccion copiar(LoteProduccion l) {
			return new LoteProduccion(l.getIdLote(), l.getIdOrden(), l.getNumeroLote(), l.getFechaInicio(), l.getFechaFin(), l.getCantidadProducida(), l.getEstado());
		}

		@Override
		public List<LoteProduccion> buscarPorOrden(int idOrden) {
			return listarTodos().stream().filter(l -> l.getIdOrden() == idOrden).toList();
		}
	}

	static class ControlesEnMemoria extends RepositorioEnMemoria<ControlCalidad>
			implements IControlCalidadRepositorio {
		ControlesEnMemoria() {
			super(ControlCalidad::getIdControl, ControlCalidad::setIdControl);
		}

		@Override
		protected ControlCalidad copiar(ControlCalidad c) {
			return new ControlCalidad(c.getIdControl(), c.getIdLote(), c.getFechaControl(), c.getPh(), c.getBrix(), c.getTemperatura(), c.getResultado(), c.getObservaciones());
		}

		/** Del mas reciente al mas antiguo, como exige el puerto. */
		@Override
		public List<ControlCalidad> buscarPorLote(int idLote) {
			return listarTodos().stream()
					.filter(c -> c.getIdLote() == idLote)
					.sorted((a, b) -> b.getIdControl().compareTo(a.getIdControl()))
					.toList();
		}
	}

	static class InventarioEnMemoria extends RepositorioEnMemoria<InventarioProducto>
			implements IInventarioProductoRepositorio {
		InventarioEnMemoria() {
			super(InventarioProducto::getIdInventario, InventarioProducto::setIdInventario);
		}

		@Override
		protected InventarioProducto copiar(InventarioProducto i) {
			return new InventarioProducto(i.getIdInventario(), i.getIdLote(), i.getCantidad(), i.getUbicacion(), i.getFechaIngreso());
		}

		@Override
		public List<InventarioProducto> buscarPorLote(int idLote) {
			return listarTodos().stream().filter(i -> i.getIdLote() == idLote).toList();
		}
	}

	private ControlesEnMemoria controles;
	private InventarioProductoUseCaseImpl useCase;
	private Integer idLote;

	@BeforeEach
	void prepararProduccion() {
		LotesEnMemoria lotes = new LotesEnMemoria();
		controles = new ControlesEnMemoria();
		useCase = new InventarioProductoUseCaseImpl(new InventarioEnMemoria(), lotes, controles);

		idLote = lotes.guardar(new LoteProduccion(null, 1, "LT-0001", LocalDateTime.now(), LocalDateTime.now(),
				new BigDecimal("500"), "FINALIZADO")).getIdLote();
	}

	private void inspeccionar(String resultado) {
		controles.guardar(new ControlCalidad(null, idLote, LocalDateTime.now(), new BigDecimal("3.0"),
				new BigDecimal("10.0"), new BigDecimal("4.0"), resultado, null));
	}

	private InventarioProducto guardar(String cantidad, String ubicacion) {
		return useCase.guardar(new InventarioProducto(null, idLote, new BigDecimal(cantidad),
				ubicacion, LocalDate.now()));
	}

	@Test
	void unLoteAprobadoEntraAlInventario() {
		inspeccionar("APROBADO");

		assertThat(guardar("500", "PASILLO A").getIdInventario()).isNotNull();
	}

	@Test
	void unLoteSinControlDeCalidadNoEntra() {
		assertThatThrownBy(() -> guardar("500", "PASILLO A"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("todavía no tiene control de calidad");
	}

	@Test
	void unLoteRechazadoNoEntra() {
		inspeccionar("RECHAZADO");

		assertThatThrownBy(() -> guardar("500", "PASILLO A"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("solo entran al inventario los lotes aprobados");
	}

	@Test
	void unLoteObservadoTampocoEntra() {
		inspeccionar("OBSERVADO");

		assertThatThrownBy(() -> guardar("500", "PASILLO A"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("solo entran al inventario los lotes aprobados");
	}

	@Test
	void mandaElControlMasReciente() {
		// el laboratorio observa el lote y despues vuelve a inspeccionarlo
		inspeccionar("OBSERVADO");
		inspeccionar("APROBADO");

		assertThat(guardar("500", "PASILLO A").getIdInventario()).isNotNull();
	}

	@Test
	void unLoteSePuedeRepartirEnVariasUbicaciones() {
		inspeccionar("APROBADO");

		guardar("300", "PASILLO A");
		guardar("200", "PASILLO B");

		assertThat(useCase.listarTodos()).hasSize(2);
	}

	@Test
	void entreTodasLasUbicacionesNoSePuedeGuardarMasDeLoProducido() {
		inspeccionar("APROBADO");
		guardar("300", "PASILLO A");

		assertThatThrownBy(() -> guardar("300", "PASILLO B"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("produjo 500 y ya hay 300 guardadas");
	}

	@Test
	void noSePuedeGuardarMasDeLoQueElLoteProdujo() {
		inspeccionar("APROBADO");

		assertThatThrownBy(() -> guardar("900", "PASILLO A"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("el lote LT-0001 produjo 500");
	}

	@Test
	void laUbicacionSeGuardaNormalizada() {
		inspeccionar("APROBADO");

		assertThat(guardar("500", "  pasillo   a - estante 1 ").getUbicacion())
				.isEqualTo("PASILLO A - ESTANTE 1");
	}
}
