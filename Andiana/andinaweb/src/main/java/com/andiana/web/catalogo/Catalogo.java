package com.andiana.web.catalogo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Todas las pantallas de gestion descritas en un solo sitio. Como cada seccion
 * dice que campos tiene y de que tipo son, un unico controlador y dos plantillas
 * alcanzan para las nueve: no hay que repetir el mismo formulario nueve veces.
 *
 * Agregar una entidad nueva es agregar una entrada a esta lista.
 */
public final class Catalogo {

	private static final List<Seccion> SECCIONES = List.of(

			new Seccion("producto", "producto", "Productos", "producto", "bi-cup-straw",
					"idProducto", "nombre", List.of(
							Campo.id("idProducto"),
							Campo.texto("nombre", "Nombre"),
							Campo.lista("presentacion", "Presentacion",
									"350 ML", "500 ML", "1 LITRO", "2 LITROS"))),

			new Seccion("materiaPrima", "materiaPrima", "Materias primas", "materia prima", "bi-droplet-half",
					"idMateriaPrima", "nombre", List.of(
							Campo.id("idMateriaPrima"),
							Campo.texto("nombre", "Nombre"),
							Campo.lista("unidadMedida", "Unidad de medida",
									"LITRO", "MILILITRO", "KILOGRAMO", "GRAMO", "UNIDAD"),
							Campo.soloLectura("stock", "Stock"))),

			new Seccion("receta", "receta", "Recetas", "receta", "bi-journal-text",
					"idReceta", "version", List.of(
							Campo.id("idReceta"),
							Campo.relacion("idProducto", "Producto", "producto"),
							Campo.texto("version", "Version"),
							Campo.fecha("fecha", "Fecha"),
							Campo.siNo("activa", "Activa"))),

			new Seccion("recetaDetalle", "recetaDetalle", "Materias de la receta", "linea de receta", "bi-list-check",
					"idRecetaDetalle", "idRecetaDetalle", List.of(
							Campo.id("idRecetaDetalle"),
							Campo.relacion("idReceta", "Receta", "receta"),
							Campo.relacion("idMateriaPrima", "Materia prima", "materiaPrima"),
							Campo.decimal("cantidad", "Cantidad"))),

			new Seccion("movimiento", "movimiento", "Movimientos de inventario", "movimiento", "bi-arrow-left-right",
					"idMovimiento", "idMovimiento", List.of(
							Campo.id("idMovimiento"),
							Campo.relacion("idMateriaPrima", "Materia prima", "materiaPrima"),
							Campo.lista("tipo", "Tipo", "INGRESO", "CONSUMO", "AJUSTE"),
							Campo.decimal("cantidad", "Cantidad"),
							Campo.fecha("fecha", "Fecha"),
							Campo.textoOpcional("observacion", "Observacion"))),

			new Seccion("orden", "orden", "Ordenes de produccion", "orden", "bi-clipboard-check",
					"idOrden", "codigo", List.of(
							Campo.id("idOrden"),
							Campo.texto("codigo", "Codigo"),
							Campo.relacion("idProducto", "Producto", "producto"),
							Campo.numero("cantidadProgramada", "Cantidad programada"),
							Campo.fecha("fechaProduccion", "Fecha de produccion"),
							Campo.lista("estado", "Estado", "PLANIFICADA", "EN PROCESO", "FINALIZADA"))),

			new Seccion("lote", "lote", "Lotes de produccion", "lote", "bi-box-seam",
					"idLote", "codigoLote", List.of(
							Campo.id("idLote"),
							Campo.texto("codigoLote", "Codigo de lote"),
							Campo.relacion("idOrden", "Orden de produccion", "orden"),
							Campo.numero("cantidadProducida", "Cantidad producida"),
							Campo.fecha("fechaFabricacion", "Fecha de fabricacion"))),

			new Seccion("controlCalidad", "controlCalidad", "Control de calidad", "control", "bi-thermometer-half",
					"idControl", "idControl", List.of(
							Campo.id("idControl"),
							Campo.relacion("idLote", "Lote", "lote"),
							Campo.decimal("ph", "pH"),
							Campo.decimal("gradosBrix", "Grados Brix"),
							Campo.decimal("temperatura", "Temperatura"),
							Campo.lista("resultado", "Resultado", "APROBADO", "OBSERVADO", "RECHAZADO"),
							Campo.fecha("fechaInspeccion", "Fecha de inspeccion"))),

			new Seccion("almacen", "almacen", "Almacen de terminados", "registro de almacen", "bi-building",
					"idAlmacen", "idAlmacen", List.of(
							Campo.id("idAlmacen"),
							Campo.relacion("idLote", "Lote aprobado", "lote"),
							Campo.numero("cantidad", "Cantidad"),
							Campo.texto("ubicacionFisica", "Ubicacion fisica"),
							Campo.fecha("fechaIngreso", "Fecha de ingreso"))));

	private static final Map<String, Seccion> POR_CLAVE = new LinkedHashMap<>();
	static {
		for (Seccion seccion : SECCIONES) {
			POR_CLAVE.put(seccion.clave(), seccion);
		}
	}

	private Catalogo() {
	}

	public static List<Seccion> todas() {
		return SECCIONES;
	}

	public static Seccion buscar(String clave) {
		Seccion seccion = POR_CLAVE.get(clave);
		if (seccion == null) {
			throw new IllegalArgumentException("No existe la seccion \"" + clave + "\"");
		}
		return seccion;
	}
}
