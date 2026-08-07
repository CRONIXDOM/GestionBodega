package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.casosuso.impl.ConsultaUseCaseImpl;
import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.entidades.Producto;
import com.andiana.api.dominio.entidades.RecetaProduccion;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;
import com.andiana.api.presentacion.dto.response.ConteoRecetaDto;
import com.andiana.api.presentacion.dto.response.MateriaEnRecetaDto;

/** Las dos consultas que pidio la gerencia. */
class ConsultaUseCaseImplTest {

	static class RecetasEnMemoria extends RepositorioEnMemoria<RecetaProduccion>
			implements IRecetaProduccionRepositorio {
		RecetasEnMemoria() {
			super(RecetaProduccion::getIdReceta, RecetaProduccion::setIdReceta);
		}

		@Override
		public List<RecetaProduccion> buscarPorProducto(int idProducto) {
			return listarTodos().stream().filter(r -> r.getIdProducto() == idProducto).toList();
		}
	}

	static class DetallesEnMemoria extends RepositorioEnMemoria<DetalleReceta>
			implements IDetalleRecetaRepositorio {
		DetallesEnMemoria() {
			super(DetalleReceta::getIdDetalle, DetalleReceta::setIdDetalle);
		}

		@Override
		public List<DetalleReceta> buscarPorReceta(int idReceta) {
			return listarTodos().stream().filter(d -> d.getIdReceta() == idReceta).toList();
		}
	}

	static class ProductosEnMemoria extends RepositorioEnMemoria<Producto> implements IProductoRepositorio {
		ProductosEnMemoria() {
			super(Producto::getIdProducto, Producto::setIdProducto);
		}
	}

	private ConsultaUseCaseImpl useCase;
	private Integer recetaV1;
	private Integer recetaV2;

	@BeforeEach
	void prepararFormulas() {
		ProductosEnMemoria productos = new ProductosEnMemoria();
		RecetasEnMemoria recetas = new RecetasEnMemoria();
		DetallesEnMemoria detalles = new DetallesEnMemoria();
		MateriasEnMemoria materias = new MateriasEnMemoria();
		useCase = new ConsultaUseCaseImpl(recetas, detalles, materias, productos);

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
		List<MateriaEnRecetaDto> materias = useCase.materiasDeLaReceta(recetaV1);

		assertThat(materias).extracting(MateriaEnRecetaDto::getMateriaPrima)
				.containsExactly("AGUA PURIFICADA", "AZUCAR", "COLORANTE CARAMELO");
		assertThat(materias.get(0).getCantidad()).isEqualByComparingTo("0.320");
		assertThat(materias.get(0).getUnidad()).isEqualTo("LITRO");
		assertThat(materias.get(0).getStockActual()).isEqualByComparingTo("100");
	}

	@Test
	void cadaRecetaListaSoloSusPropiasMaterias() {
		assertThat(useCase.materiasDeLaReceta(recetaV2))
				.extracting(MateriaEnRecetaDto::getMateriaPrima)
				.containsExactly("AGUA PURIFICADA", "AZUCAR");
	}

	@Test
	void avisaSiLaRecetaNoExiste() {
		assertThatThrownBy(() -> useCase.materiasDeLaReceta(9999))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("La receta indicada no existe");
	}

	@Test
	void cuentaLasMateriasPrimasDeCadaReceta() {
		List<ConteoRecetaDto> conteo = useCase.conteoDeMateriasPorReceta();

		assertThat(conteo).hasSize(2);
		assertThat(conteo).extracting(ConteoRecetaDto::getVersion).containsExactly(1, 2);
		assertThat(conteo).extracting(ConteoRecetaDto::getTotalMateriasPrimas).containsExactly(3L, 2L);
		assertThat(conteo.get(0).getProducto()).isEqualTo("COLA ANDINA BOTELLA PET (350 ml)");
		assertThat(conteo.get(0).getActiva()).isFalse();
		assertThat(conteo.get(1).getActiva()).isTrue();
	}

	@Test
	void unaRecetaSinMateriasPrimasCuentaCero() {
		RecetasEnMemoria recetas = new RecetasEnMemoria();
		ProductosEnMemoria productos = new ProductosEnMemoria();
		Integer producto = productos.guardar(new Producto(null, "LIMON ANDINA", "GASEOSA", "BOTELLA 1L", 1000, true))
				.getIdProducto();
		recetas.guardar(new RecetaProduccion(null, producto, 1, LocalDate.now(), true));

		List<ConteoRecetaDto> conteo = new ConsultaUseCaseImpl(recetas, new DetallesEnMemoria(),
				new MateriasEnMemoria(), productos).conteoDeMateriasPorReceta();

		assertThat(conteo).singleElement()
				.extracting(ConteoRecetaDto::getTotalMateriasPrimas).isEqualTo(0L);
	}
}
