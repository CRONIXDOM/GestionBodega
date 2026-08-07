package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.servicio.ServicioControlCalidad;
import com.andiana.api.aplicacion.servicio.ServicioInventarioProducto;
import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.modelo.ControlCalidad;
import com.andiana.api.dominio.modelo.InventarioProducto;
import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.InventarioProductoRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;

/** La regla central: al inventario solo entra lo que el laboratorio aprobo. */
class ServicioInventarioTest {

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

		/** Del mas reciente al mas antiguo, como exige el puerto. */
		@Override
		public List<ControlCalidad> buscarPorLote(Integer idLote) {
			return listar().stream()
					.filter(c -> idLote.equals(c.getIdLote()))
					.sorted((a, b) -> b.getIdControl().compareTo(a.getIdControl()))
					.toList();
		}
	}

	static class InventarioEnMemoria extends RepositorioEnMemoria<InventarioProducto>
			implements InventarioProductoRepositorio {
		InventarioEnMemoria() {
			super(InventarioProducto::getIdInventario, InventarioProducto::setIdInventario);
		}

		@Override
		public List<InventarioProducto> buscarPorLote(Integer idLote) {
			return listar().stream().filter(a -> idLote.equals(a.getIdLote())).toList();
		}
	}

	private ControlesEnMemoria controles;
	private ServicioInventarioProducto servicio;
	private Integer idLote;

	@BeforeEach
	void prepararProduccion() {
		LotesEnMemoria lotes = new LotesEnMemoria();
		controles = new ControlesEnMemoria();
		InventarioEnMemoria inventario = new InventarioEnMemoria();
		ServicioControlCalidad servicioControl = new ServicioControlCalidad(controles, lotes, inventario);
		servicio = new ServicioInventarioProducto(inventario, lotes, controles, servicioControl);

		idLote = lotes.guardar(new LoteProduccion(null, 1, "LT-0001", LocalDateTime.now(), LocalDateTime.now(),
				new BigDecimal("500"), "FINALIZADO")).getIdLote();
	}

	private void inspeccionar(String resultado) {
		controles.guardar(new ControlCalidad(null, idLote, LocalDateTime.now(), new BigDecimal("3.0"),
				new BigDecimal("10.0"), new BigDecimal("4.0"), resultado, null));
	}

	private InventarioProducto guardar(String cantidad, String ubicacion) {
		return servicio.guardar(new InventarioProducto(null, idLote, new BigDecimal(cantidad),
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
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("todavia no tiene control de calidad");
	}

	@Test
	void unLoteRechazadoNoEntra() {
		inspeccionar("RECHAZADO");

		assertThatThrownBy(() -> guardar("500", "PASILLO A"))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("solo entran al inventario los lotes aprobados");
	}

	@Test
	void unLoteObservadoTampocoEntra() {
		inspeccionar("OBSERVADO");

		assertThatThrownBy(() -> guardar("500", "PASILLO A"))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("solo entran al inventario los lotes aprobados");
	}

	@Test
	void manda_elControlMasReciente() {
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

		assertThat(servicio.listar()).hasSize(2);
	}

	@Test
	void entreTodasLasUbicacionesNoSePuedeGuardarMasDeLoProducido() {
		inspeccionar("APROBADO");
		guardar("300", "PASILLO A");

		assertThatThrownBy(() -> guardar("300", "PASILLO B"))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("produjo 500 y ya hay 300 guardadas");
	}

	@Test
	void noSePuedeGuardarMasDeLoQueElLoteProdujo() {
		inspeccionar("APROBADO");

		assertThatThrownBy(() -> guardar("900", "PASILLO A"))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("el lote LT-0001 produjo 500");
	}

	@Test
	void laUbicacionSeGuardaNormalizada() {
		inspeccionar("APROBADO");

		assertThat(guardar("500", "  pasillo   a - estante 1 ").getUbicacion())
				.isEqualTo("PASILLO A - ESTANTE 1");
	}
}
