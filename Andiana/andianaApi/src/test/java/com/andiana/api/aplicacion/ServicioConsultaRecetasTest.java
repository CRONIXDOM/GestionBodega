package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.puerto.ConsultaRecetas;
import com.andiana.api.aplicacion.servicio.ServicioConsultaRecetas;
import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.modelo.DetalleReceta;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.dominio.modelo.RecetaProduccion;
import com.andiana.api.dominio.puerto.DetalleRecetaRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaProduccionRepositorio;

/** Las dos consultas que pidio la gerencia. */
class ServicioConsultaRecetasTest {

	static class RecetasEnMemoria extends RepositorioEnMemoria<RecetaProduccion>
			implements RecetaProduccionRepositorio {
		RecetasEnMemoria() {
			super(RecetaProduccion::getIdReceta, RecetaProduccion::setIdReceta);
		}

		@Override
		public List<RecetaProduccion> buscarPorProducto(Integer idProducto) {
			return listar().stream().filter(r -> idProducto.equals(r.getIdProducto())).toList();
		}
	}

	static class DetallesEnMemoria extends RepositorioEnMemoria<DetalleReceta> implements DetalleRecetaRepositorio {
		DetallesEnMemoria() {
			super(DetalleReceta::getIdDetalle, DetalleReceta::setIdDetalle);
		}

		@Override
		public List<DetalleReceta> buscarPorReceta(Integer idReceta) {
			return listar().stream().filter(d -> idReceta.equals(d.getIdReceta())).toList();
		}
	}

	static class ProductosEnMemoria extends RepositorioEnMemoria<Producto> implements ProductoRepositorio {
		ProductosEnMemoria() {
			super(Producto::getIdProducto, Producto::setIdProducto);
		}
	}

	private ServicioConsultaRecetas consultas;
	private Integer recetaV1;
	private Integer recetaV2;

	@BeforeEach
	void prepararFormulas() {
		ProductosEnMemoria productos = new ProductosEnMemoria();
		RecetasEnMemoria recetas = new RecetasEnMemoria();
		DetallesEnMemoria detalles = new DetallesEnMemoria();
		MateriasEnMemoria materias = new MateriasEnMemoria();
		consultas = new ServicioConsultaRecetas(recetas, detalles, materias, productos);

		Integer cola = productos.guardar(new Producto(null, "COLA ANDINA", "GASEOSA", "BOTELLA PET", 350, true))
				.getIdProducto();

		Integer agua = materias.guardar(new MateriaPrima(null, "AGUA PURIFICADA", "LITRO",
				new BigDecimal("100"), BigDecimal.ZERO)).getIdMateria();
		Integer azucar = materias.guardar(new MateriaPrima(null, "AZUCAR", "KILOGRAMO",
				new BigDecimal("50"), BigDecimal.ZERO)).getIdMateria();
		Integer colorante = materias.guardar(new MateriaPrima(null, "COLORANTE CARAMELO", "GRAMO",
				new BigDecimal("900"), BigDecimal.ZERO)).getIdMateria();

		recetaV1 = recetas.guardar(new RecetaProduccion(null, cola, 1, LocalDate.parse("2026-01-15"), false))
				.getIdReceta();
		recetaV2 = recetas.guardar(new RecetaProduccion(null, cola, 2, LocalDate.parse("2026-06-01"), true))
				.getIdReceta();

		detalles.guardar(new DetalleReceta(null, recetaV1, agua, new BigDecimal("0.320"), "LITRO"));
		detalles.guardar(new DetalleReceta(null, recetaV1, azucar, new BigDecimal("0.035"), "KILOGRAMO"));
		detalles.guardar(new DetalleReceta(null, recetaV1, colorante, new BigDecimal("1.200"), "GRAMO"));
		// la version 2 quito el colorante
		detalles.guardar(new DetalleReceta(null, recetaV2, agua, new BigDecimal("0.325"), "LITRO"));
		detalles.guardar(new DetalleReceta(null, recetaV2, azucar, new BigDecimal("0.030"), "KILOGRAMO"));
	}

	@Test
	void listaLasMateriasPrimasDeUnaReceta() {
		List<ConsultaRecetas.MateriaEnReceta> materias = consultas.materiasDeLaReceta(recetaV1);

		assertThat(materias).extracting(ConsultaRecetas.MateriaEnReceta::materiaPrima)
				.containsExactly("AGUA PURIFICADA", "AZUCAR", "COLORANTE CARAMELO");
		assertThat(materias.get(0).cantidad()).isEqualByComparingTo("0.320");
		assertThat(materias.get(0).unidad()).isEqualTo("LITRO");
		assertThat(materias.get(0).stockActual()).isEqualByComparingTo("100");
	}

	@Test
	void cadaRecetaListaSoloSusPropiasMaterias() {
		assertThat(consultas.materiasDeLaReceta(recetaV2))
				.extracting(ConsultaRecetas.MateriaEnReceta::materiaPrima)
				.containsExactly("AGUA PURIFICADA", "AZUCAR");
	}

	@Test
	void avisaSiLaRecetaNoExiste() {
		assertThatThrownBy(() -> consultas.materiasDeLaReceta(9999))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("La receta indicada no existe");
	}

	@Test
	void cuentaLasMateriasPrimasDeCadaReceta() {
		List<ConsultaRecetas.ConteoDeReceta> conteo = consultas.conteoDeMateriasPorReceta();

		assertThat(conteo).hasSize(2);
		assertThat(conteo).extracting(ConsultaRecetas.ConteoDeReceta::version).containsExactly(1, 2);
		assertThat(conteo).extracting(ConsultaRecetas.ConteoDeReceta::totalMateriasPrimas)
				.containsExactly(3L, 2L);
		assertThat(conteo.get(0).producto()).isEqualTo("COLA ANDINA BOTELLA PET (350 ml)");
		assertThat(conteo.get(0).activa()).isFalse();
		assertThat(conteo.get(1).activa()).isTrue();
	}

	@Test
	void unaRecetaSinMateriasPrimasCuentaCero() {
		RecetasEnMemoria recetas = new RecetasEnMemoria();
		ProductosEnMemoria productos = new ProductosEnMemoria();
		Integer producto = productos.guardar(new Producto(null, "LIMON ANDINA", "GASEOSA", "BOTELLA 1L", 1000, true))
				.getIdProducto();
		recetas.guardar(new RecetaProduccion(null, producto, 1, LocalDate.now(), true));

		List<ConsultaRecetas.ConteoDeReceta> conteo = new ServicioConsultaRecetas(recetas,
				new DetallesEnMemoria(), new MateriasEnMemoria(), productos).conteoDeMateriasPorReceta();

		assertThat(conteo).singleElement()
				.extracting(ConsultaRecetas.ConteoDeReceta::totalMateriasPrimas).isEqualTo(0L);
	}
}
