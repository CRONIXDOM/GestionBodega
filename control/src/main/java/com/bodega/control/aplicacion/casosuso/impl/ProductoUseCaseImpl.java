package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bodega.control.aplicacion.casosuso.entrada.IProductoUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.repositorio.IDetalleEntregaRepositorio;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;
import com.bodega.control.dominio.repositorio.IProductoRepositorio;
import com.bodega.control.dominio.repositorio.IRegistroRepositorio;

public class ProductoUseCaseImpl implements IProductoUseCase {

    public static final String ACTIVO = "ACTIVO";
    public static final String ELIMINADO = "ELIMINADO";

    private final IProductoRepositorio repositorio;
    private final ILoteRepositorio loteRepositorio;
    private final IRegistroRepositorio registroRepositorio;
    private final IDetalleEntregaRepositorio detalleEntregaRepositorio;

    public ProductoUseCaseImpl(IProductoRepositorio repositorio, ILoteRepositorio loteRepositorio,
            IRegistroRepositorio registroRepositorio, IDetalleEntregaRepositorio detalleEntregaRepositorio) {
        this.repositorio = repositorio;
        this.loteRepositorio = loteRepositorio;
        this.registroRepositorio = registroRepositorio;
        this.detalleEntregaRepositorio = detalleEntregaRepositorio;
    }

    @Override
    public Producto guardar(Producto nuevoProducto) {
        nuevoProducto.setNombreProducto(Validaciones.normalizar(nuevoProducto.getNombreProducto()));
        nuevoProducto.setCodigoProducto(Validaciones.normalizar(nuevoProducto.getCodigoProducto()));
        nuevoProducto.setCantidadProducto(Validaciones.normalizar(nuevoProducto.getCantidadProducto()));

        Validaciones.obligatorio(nuevoProducto.getNombreProducto(), "nombre del producto");
        Validaciones.obligatorio(nuevoProducto.getCodigoProducto(), "código del producto");
        Validaciones.obligatorioPositivo(nuevoProducto.getUnidadesPorCaja(), "unidades por caja");

        // el formulario no manda el estado: al editar se conserva el que tenia y
        // al crear entra como activo
        if (nuevoProducto.getIdProducto() == null) {
            nuevoProducto.setEstado(ACTIVO);
        } else {
            nuevoProducto.setEstado(buscarPorId(nuevoProducto.getIdProducto()).getEstado());
        }

        List<Producto> existentes = repositorio.listarTodos();

        // el nombre y el codigo los sigue ocupando un producto dado de baja: si no
        // se avisa, el usuario ve un "ya existe" de algo que no aparece en la lista
        avisarSiLoTieneUnEliminado(existentes, nuevoProducto);

        Validaciones.noRepetido(existentes, Producto::getIdProducto, Producto::getCodigoProducto,
                nuevoProducto.getIdProducto(), nuevoProducto.getCodigoProducto(), "un producto con el código");
        Validaciones.noRepetido(existentes, Producto::getIdProducto, Producto::getNombreProducto,
                nuevoProducto.getIdProducto(), nuevoProducto.getNombreProducto(), "un producto con el nombre");

        return repositorio.guardar(nuevoProducto);
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        return repositorio.buscarPorid(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Producto buscarPorid(int idProducto) {
        return buscarPorId(idProducto);
    }

    @Override
    public List<Producto> listarTodos() {
        return repositorio.listarTodos();
    }

    /**
     * Eliminar un producto es darlo de baja, no borrarlo de la tabla.
     *
     * Los movimientos, las entregas y las solicitudes apuntan al producto: si se
     * borrara de verdad, o habria que borrar tambien ese historial -y la bodega
     * perderia su trazabilidad- o la base de datos lo impediria. Asi el producto
     * desaparece de los listados y de los selectores, el historial sigue
     * completo y se puede recuperar cuando haga falta.
     *
     * Lo unico que frena la baja es que todavia quede mercaderia: no se hace
     * desaparecer stock ocultando la ficha. Con los lotes ya vacios se da de
     * baja sin mas, y esos lotes vacios se van con el producto.
     */
    @Override
    @Transactional
    public void eliminar(int idProducto) {
        Producto producto = buscarPorId(idProducto);
        if (ELIMINADO.equals(producto.getEstado())) {
            return;
        }

        List<Lote> lotes = loteRepositorio.buscarPorProductoOrdenadoFifo(idProducto);
        int enBodega = lotes.stream().mapToInt(lote -> valor(lote.getCantidadLote())).sum();
        int reservado = lotes.stream().mapToInt(lote -> valor(lote.getCantidadReservada())).sum();
        if (enBodega > 0 || reservado > 0) {
            throw new RuntimeException("No se puede eliminar " + producto.getNombreProducto()
                    + ": todavía quedan " + (enBodega + reservado) + " unidades en "
                    + lotes.size() + (lotes.size() == 1 ? " lote" : " lotes")
                    + ". Despacha o da de baja la mercadería primero.");
        }

        // los lotes vacios que nadie mas usa se borran; los que estan en el
        // historial se quedan, pero dejan de verse porque su producto esta de baja
        for (Lote lote : lotes) {
            if (!estaEnElHistorial(lote.getIdLote())) {
                loteRepositorio.eliminar(lote.getIdLote());
            }
        }

        producto.setEstado(ELIMINADO);
        repositorio.guardar(producto);
    }

    /** Devuelve al producto a los listados, tal como estaba. */
    @Override
    public Producto recuperar(int idProducto) {
        Producto producto = buscarPorId(idProducto);
        producto.setEstado(ACTIVO);
        return repositorio.guardar(producto);
    }

    /** Los que se ven en el sistema: los que no estan dados de baja. */
    @Override
    public List<Producto> listarActivos() {
        return repositorio.listarTodos().stream().filter(ProductoUseCaseImpl::estaActivo).toList();
    }

    /** Los dados de baja, para poder recuperarlos. */
    @Override
    public List<Producto> listarEliminados() {
        return repositorio.listarTodos().stream().filter(producto -> !estaActivo(producto)).toList();
    }

    private void avisarSiLoTieneUnEliminado(List<Producto> existentes, Producto nuevoProducto) {
        for (Producto otro : existentes) {
            if (estaActivo(otro) || otro.getIdProducto().equals(nuevoProducto.getIdProducto())) {
                continue;
            }
            boolean mismoNombre = otro.getNombreProducto() != null
                    && otro.getNombreProducto().equals(nuevoProducto.getNombreProducto());
            boolean mismoCodigo = otro.getCodigoProducto() != null
                    && otro.getCodigoProducto().equals(nuevoProducto.getCodigoProducto());
            if (mismoNombre || mismoCodigo) {
                throw new RuntimeException("Ese " + (mismoNombre ? "nombre" : "código")
                        + " lo tiene " + otro.getNombreProducto()
                        + ", que está en productos eliminados. Recupéralo en vez de crearlo de nuevo, "
                        + "o usa otro " + (mismoNombre ? "nombre" : "código") + ".");
            }
        }
    }

    /**
     * Un producto guardado antes de que existiera la columna llega sin estado:
     * cuenta como activo.
     */
    private static boolean estaActivo(Producto producto) {
        return !ELIMINADO.equals(producto.getEstado());
    }

    /** Si algun movimiento o alguna entrega apunta a este lote, el lote se queda. */
    private boolean estaEnElHistorial(Integer idLote) {
        if (idLote == null) {
            return false;
        }
        boolean enRegistros = registroRepositorio.listarTodos().stream()
                .anyMatch(registro -> registro.getLote() != null
                        && idLote.equals(registro.getLote().getIdLote()));
        if (enRegistros) {
            return true;
        }
        return detalleEntregaRepositorio.listarTodos().stream()
                .anyMatch(detalle -> detalle.getLote() != null
                        && idLote.equals(detalle.getLote().getIdLote()));
    }

    private int valor(Integer cantidad) {
        return cantidad == null ? 0 : cantidad;
    }
}