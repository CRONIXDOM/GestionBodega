package com.andiana.web.catalogo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Todas las pantallas de gestion descritas en un solo sitio. Como cada seccion
 * dice que campos tiene y de que tipo son, un unico controlador y dos plantillas
 * alcanzan para las nueve: no hay que repetir el mismo formulario nueve veces.
 *
 * Los valores de las listas cerradas son exactamente los que admiten los CHECK
 * de la base de datos, para que la pantalla no ofrezca nada que la base vaya a
 * rechazar despues.
 *
 * Agregar una entidad nueva es agregar una entrada a esta lista.
 */
public final class Catalogo {

	private static final List<Seccion> SECCIONES = List.of(

			new Seccion("producto", "producto", "Productos", "producto", "id_producto",
					"idProducto", "nombre", List.of(
							Campo.id("idProducto"),
							Campo.texto("nombre", "Nombre"),
							Campo.texto("tipo", "Tipo"),
							Campo.texto("presentacion", "Presentacion"),
							Campo.numero("volumenMl", "Volumen (ml)"),
							Campo.siNo("estado", "Activo"))),

			new Seccion("materiaPrima", "materiaPrima", "Materias primas", "materia prima", "id_materia",
					"idMateria", "nombre", List.of(
							Campo.id("idMateria"),
							Campo.texto("nombre", "Nombre"),
							Campo.texto("unidadMedida", "Unidad de medida"),
							Campo.soloLectura("stockActual", "Stock actual"),
							Campo.decimalOpcional("stockMinimo", "Stock minimo"))),

			new Seccion("receta", "receta", "Recetas", "receta", "id_receta",
					"idReceta", "version", List.of(
							Campo.id("idReceta"),
							Campo.relacion("idProducto", "Producto", "producto"),
							Campo.numero("version", "Version"),
							Campo.fecha("fechaVigencia", "Fecha de vigencia"),
							Campo.siNo("estado", "Vigente"))),

			new Seccion("detalleReceta", "detalleReceta", "Detalle de recetas", "linea de receta", "id_detalle",
					"idDetalle", "idDetalle", List.of(
							Campo.id("idDetalle"),
							Campo.relacion("idReceta", "Receta", "receta"),
							Campo.relacion("idMateria", "Materia prima", "materiaPrima"),
							Campo.decimal("cantidad", "Cantidad"),
							Campo.soloLectura("unidad", "Unidad"))),

			new Seccion("movimiento", "movimiento", "Movimientos de materia prima", "movimiento", "id_movimiento",
					"idMovimiento", "idMovimiento", List.of(
							Campo.id("idMovimiento"),
							Campo.relacion("idMateria", "Materia prima", "materiaPrima"),
							Campo.lista("tipo", "Tipo", "INGRESO", "CONSUMO", "AJUSTE"),
							Campo.decimal("cantidad", "Cantidad"),
							Campo.fechaHora("fecha", "Fecha"),
							Campo.textoOpcional("observacion", "Observacion"))),

			new Seccion("orden", "orden", "Ordenes de produccion", "orden", "id_orden",
					"idOrden", "idOrden", List.of(
							Campo.id("idOrden"),
							Campo.relacion("idProducto", "Producto", "producto"),
							Campo.fecha("fechaProgramada", "Fecha programada"),
							Campo.decimal("cantidadProgramada", "Cantidad programada"),
							Campo.lista("estado", "Estado",
									"PLANIFICADA", "EN_PROCESO", "FINALIZADA", "CANCELADA"),
							Campo.textoOpcional("responsable", "Responsable"))),

			new Seccion("lote", "lote", "Lotes de produccion", "lote", "id_lote",
					"idLote", "numeroLote", List.of(
							Campo.id("idLote"),
							Campo.texto("numeroLote", "Numero de lote"),
							Campo.relacion("idOrden", "Orden de produccion", "orden"),
							Campo.fechaHoraOpcional("fechaInicio", "Inicio"),
							Campo.fechaHoraOpcional("fechaFin", "Fin"),
							Campo.decimalOpcional("cantidadProducida", "Cantidad producida"),
							Campo.lista("estado", "Estado", "EN_PROCESO", "FINALIZADO", "RECHAZADO"))),

			new Seccion("controlCalidad", "controlCalidad", "Control de calidad", "control", "id_control",
					"idControl", "idControl", List.of(
							Campo.id("idControl"),
							Campo.relacion("idLote", "Lote", "lote"),
							Campo.fechaHora("fechaControl", "Fecha del control"),
							Campo.decimal("ph", "pH"),
							Campo.decimal("brix", "Grados Brix"),
							Campo.decimal("temperatura", "Temperatura"),
							Campo.lista("resultado", "Resultado", "APROBADO", "OBSERVADO", "RECHAZADO"),
							Campo.textoOpcional("observaciones", "Observaciones"))),

			new Seccion("inventario", "inventario", "Inventario de terminados", "registro de inventario",
					"id_inventario", "idInventario", "idInventario", List.of(
							Campo.id("idInventario"),
							Campo.relacion("idLote", "Lote aprobado", "lote"),
							Campo.decimal("cantidad", "Cantidad"),
							Campo.texto("ubicacion", "Ubicacion"),
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
