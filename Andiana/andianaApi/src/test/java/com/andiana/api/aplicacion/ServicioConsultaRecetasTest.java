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
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.dominio.modelo.Receta;
import com.andiana.api.dominio.modelo.RecetaDetalle;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaDetalleRepositorio;
import com.andiana.api.dominio.puerto.RecetaRepositorio;

/** Las dos consultas que pidio la gerencia. */
class ServicioConsultaRecetasTest {

	static class RecetasEnMemoria extends RepositorioEnMemoria<Receta> implements RecetaRepositorio {
		RecetasEnMemoria() {
			super(Receta::getIdReceta, Receta::setIdReceta);
		}

		@Override
		public List<Receta> buscarPorProducto(Integer idProducto) {
			return listar().stream().filter(r -> idProducto.equals(r.getIdProducto())).toList();
		}
	}

	static class DetallesEnMemoria extends RepositorioEnMemoria<RecetaDetalle> implements RecetaDetalleRepositorio {
		DetallesEnMemoria() {
			super(RecetaDetalle::getIdRecetaDetalle, RecetaDetalle::setIdRecetaDetalle);
		}

		@Override
		public List<RecetaDetalle> buscarPorReceta(Integer idReceta) {
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

		Integer cola = productos.guardar(new Producto(null, "COLA ANDINA", "350 ML")).getIdProducto();

		Integer agua = materias.guardar(new MateriaPrima(null, "AGUA PURIFICADA", "LITRO", BigDecimal.ZERO))
				.getIdMateriaPrima();
		Integer azucar = materias.guardar(new MateriaPrima(null, "AZUCAR", "KILOGRAMO", BigDecimal.ZERO))
				.getIdMateriaPrima();
		Integer colorante = materias.guardar(new MateriaPrima(null, "COLORANTE CARAMELO", "GRAMO", BigDecimal.ZERO))
				.getIdMateriaPrima();

		recetaV1 = recetas.guardar(new Receta(null, cola, "V1", LocalDate.parse("2026-01-15"), false)).getIdReceta();
		recetaV2 = recetas.guardar(new Receta(null, cola, "V2", LocalDate.parse("2026-06-01"), true)).getIdReceta();

		detalles.guardar(new RecetaDetalle(null, recetaV1, agua, new BigDecimal("0.320")));
		detalles.guardar(new RecetaDetalle(null, recetaV1, azucar, new BigDecimal("0.035")));
		detalles.guardar(new RecetaDetalle(null, recetaV1, colorante, new BigDecimal("1.200")));
		// la V2 quito el colorante
		detalles.guardar(new RecetaDetalle(null, recetaV2, agua, new BigDecimal("0.325")));
		detalles.guardar(new RecetaDetalle(null, recetaV2, azucar, new BigDecimal("0.030")));
	}

	@Test
	void listaLasMateriasPrimasDeUnaReceta() {
		List<ConsultaRecetas.MateriaEnReceta> materias = consultas.materiasDeLaReceta(recetaV1);

		assertThat(materias).extracting(ConsultaRecetas.MateriaEnReceta::materiaPrima)
				.containsExactly("AGUA PURIFICADA", "AZUCAR", "COLORANTE CARAMELO");
		assertThat(materias.get(0).cantidad()).isEqualByComparingTo("0.320");
		assertThat(materias.get(0).unidadMedida()).isEqualTo("LITRO");
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
		assertThat(conteo).extracting(ConsultaRecetas.ConteoDeReceta::version)
				.containsExactly("V1", "V2");
		assertThat(conteo).extracting(ConsultaRecetas.ConteoDeReceta::totalMateriasPrimas)
				.containsExactly(3L, 2L);
		assertThat(conteo.get(0).producto()).isEqualTo("COLA ANDINA 350 ML");
	}

	@Test
	void unaRecetaSinMateriasPrimasCuentaCero() {
		RecetasEnMemoria recetas = new RecetasEnMemoria();
		ProductosEnMemoria productos = new ProductosEnMemoria();
		Integer producto = productos.guardar(new Producto(null, "LIMON ANDINA", "1 LITRO")).getIdProducto();
		recetas.guardar(new Receta(null, producto, "V1", LocalDate.now(), true));

		List<ConsultaRecetas.ConteoDeReceta> conteo = new ServicioConsultaRecetas(recetas,
				new DetallesEnMemoria(), new MateriasEnMemoria(), productos).conteoDeMateriasPorReceta();

		assertThat(conteo).singleElement()
				.extracting(ConsultaRecetas.ConteoDeReceta::totalMateriasPrimas).isEqualTo(0L);
	}
}
