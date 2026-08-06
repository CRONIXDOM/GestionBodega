package com.bodega.control.aplicacion.util;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Reglas de datos comunes a todos los casos de uso: dejar el texto siempre en
 * mayúsculas antes de guardarlo, exigir los campos obligatorios y comprobar que
 * un valor no esté ya usado por otro registro.
 */
public final class Validaciones {

    private Validaciones() {
    }

    /**
     * Todo el texto se guarda en mayúsculas sin importar cómo lo escriba el
     * usuario. Además de recortar los extremos se juntan los espacios de en
     * medio: "  bodega   norte  " y "Bodega Norte" son el mismo nombre, y sin
     * esto se colaban como dos registros distintos que en pantalla se ven igual.
     */
    public static String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        String recortado = valor.trim().replaceAll("\\s+", " ");
        return recortado.isEmpty() ? null : recortado.toUpperCase();
    }

    public static void obligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new RuntimeException("El campo " + campo + " es obligatorio");
        }
    }

    public static void obligatorioPositivo(Integer valor, String campo) {
        if (valor == null) {
            throw new RuntimeException("El campo " + campo + " es obligatorio");
        }
        if (valor <= 0) {
            throw new RuntimeException("El campo " + campo + " debe ser mayor que cero");
        }
    }

    public static void obligatorioValor(Object valor, String campo) {
        if (valor == null) {
            throw new RuntimeException("El campo " + campo + " es obligatorio");
        }
    }

    /**
     * Para los campos que apuntan a otra tabla. El mapeador crea siempre el objeto
     * anidado aunque el formulario no haya elegido nada, así que no basta con mirar
     * si es null: hay que comprobar que traiga id.
     */
    public static <T> void obligatorioRelacion(T objeto, Function<T, Integer> obtenerId, String campo) {
        if (objeto == null || obtenerId.apply(objeto) == null) {
            throw new RuntimeException("El campo " + campo + " es obligatorio");
        }
    }

    /**
     * Rechaza el valor si ya lo usa OTRO registro. Al editar hay que excluir el
     * propio registro de la comparación, o guardarlo sin cambiar nada chocaría
     * consigo mismo.
     */
    public static <T> void noRepetido(List<T> existentes, Function<T, Integer> obtenerId,
            Function<T, String> obtenerValor, Integer idActual, String valor, String descripcion) {
        if (valor == null) {
            return;
        }
        boolean repetido = existentes.stream()
                .filter(otro -> idActual == null || !idActual.equals(obtenerId.apply(otro)))
                .anyMatch(otro -> valor.equalsIgnoreCase(obtenerValor.apply(otro)));
        if (repetido) {
            throw new RuntimeException("Ya existe " + descripcion + " \"" + valor + "\"");
        }
    }

    public static <T> void noRepetido(List<T> existentes, Function<T, Integer> obtenerId,
            Function<T, String> obtenerValor, Integer idActual, String valor, String descripcion,
            boolean ignorarNulos) {
        if (ignorarNulos && Objects.isNull(valor)) {
            return;
        }
        noRepetido(existentes, obtenerId, obtenerValor, idActual, valor, descripcion);
    }
}
