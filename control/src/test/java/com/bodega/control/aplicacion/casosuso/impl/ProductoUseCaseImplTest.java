package com.bodega.control.aplicacion.casosuso.impl;

import static com.bodega.control.aplicacion.casosuso.impl.ProductoUseCaseImpl.ACTIVO;
import static com.bodega.control.aplicacion.casosuso.impl.ProductoUseCaseImpl.ELIMINADO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.dominio.repositorio.IDetalleEntregaRepositorio;
import com.bodega.control.dominio.repositorio.ILoteRepositorio;
import com.bodega.control.dominio.repositorio.IProductoRepositorio;
import com.bodega.control.dominio.repositorio.IRegistroRepositorio;

/**
 * Eliminar un producto es darlo de baja: sale de los listados, el historial
 * queda intacto y se puede recuperar.
 */
@ExtendWith(MockitoExtension.class)
class ProductoUseCaseImplTest {

    private static final int ID_PRODUCTO = 10;

    @Mock
    private IProductoRepositorio repositorio;
    @Mock
    private ILoteRepositorio loteRepositorio;
    @Mock
    private IRegistroRepositorio registroRepositorio;
    @Mock
    private IDetalleEntregaRepositorio detalleEntregaRepositorio;

    private ProductoUseCaseImpl useCase;
    private Producto producto;

    @BeforeEach
    void setUp() {
        useCase = new ProductoUseCaseImpl(repositorio, loteRepositorio, registroRepositorio,
                detalleEntregaRepositorio);

        producto = producto(ID_PRODUCTO, "ARROZ", "PRD-001", ACTIVO);
        lenient().when(repositorio.buscarPorid(ID_PRODUCTO)).thenReturn(Optional.of(producto));
        lenient().when(registroRepositorio.listarTodos()).thenReturn(List.of());
        lenient().when(detalleEntregaRepositorio.listarTodos()).thenReturn(List.of());
        lenient().when(repositorio.guardar(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    private Producto producto(Integer id, String nombre, String codigo, String estado) {
        Producto p = new Producto();
        p.setIdProducto(id);
        p.setNombreProducto(nombre);
        p.setCodigoProducto(codigo);
        p.setUnidadesPorCaja(12);
        p.setEstado(estado);
        return p;
    }

    private Lote lote(int idLote, Integer cantidad, Integer reservada) {
        Lote lote = new Lote();
        lote.setIdLote(idLote);
        lote.setCantidadLote(cantidad);
        lote.setCantidadReservada(reservada);
        return lote;
    }

    // ---------- dar de baja ----------

    @Test
    void conLotesVacios_seDaDeBajaYSusLotesVaciosSeVan() {
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(ID_PRODUCTO))
                .thenReturn(List.of(lote(1, 0, 0), lote(2, 0, 0)));

        useCase.eliminar(ID_PRODUCTO);

        assertThat(producto.getEstado()).isEqualTo(ELIMINADO);
        verify(loteRepositorio).eliminar(1);
        verify(loteRepositorio).eliminar(2);
        verify(repositorio, never()).eliminar(ID_PRODUCTO);
    }

    @Test
    void elLoteQueNombraElHistorialNoSeBorra() {
        Lote conHistorial = lote(1, 0, 0);
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(ID_PRODUCTO)).thenReturn(List.of(conHistorial));
        Registro registro = new Registro();
        registro.setLote(conHistorial);
        when(registroRepositorio.listarTodos()).thenReturn(List.of(registro));

        useCase.eliminar(ID_PRODUCTO);

        assertThat(producto.getEstado()).isEqualTo(ELIMINADO);
        verify(loteRepositorio, never()).eliminar(1);
    }

    @Test
    void elLoteQueNombraUnaEntregaTampocoSeBorra() {
        Lote conEntrega = lote(1, 0, 0);
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(ID_PRODUCTO)).thenReturn(List.of(conEntrega));
        DetalleEntrega detalle = new DetalleEntrega();
        detalle.setLote(conEntrega);
        when(detalleEntregaRepositorio.listarTodos()).thenReturn(List.of(detalle));

        useCase.eliminar(ID_PRODUCTO);

        verify(loteRepositorio, never()).eliminar(1);
    }

    @Test
    void sinLotes_seDaDeBaja() {
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(ID_PRODUCTO)).thenReturn(List.of());

        useCase.eliminar(ID_PRODUCTO);

        assertThat(producto.getEstado()).isEqualTo(ELIMINADO);
    }

    @Test
    void conMercaderia_noSeDaDeBaja() {
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(ID_PRODUCTO))
                .thenReturn(List.of(lote(1, 0, 0), lote(2, 40, 0)));

        assertThatThrownBy(() -> useCase.eliminar(ID_PRODUCTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ARROZ")
                .hasMessageContaining("40 unidades");

        assertThat(producto.getEstado()).isEqualTo(ACTIVO);
        verify(loteRepositorio, never()).eliminar(1);
    }

    @Test
    void loQueEstaReservadoTambienCuenta() {
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(ID_PRODUCTO)).thenReturn(List.of(lote(1, 0, 5)));

        assertThatThrownBy(() -> useCase.eliminar(ID_PRODUCTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("5 unidades");

        assertThat(producto.getEstado()).isEqualTo(ACTIVO);
    }

    @Test
    void loteSinCantidadRegistrada_cuentaComoVacio() {
        when(loteRepositorio.buscarPorProductoOrdenadoFifo(ID_PRODUCTO)).thenReturn(List.of(lote(1, null, null)));

        useCase.eliminar(ID_PRODUCTO);

        assertThat(producto.getEstado()).isEqualTo(ELIMINADO);
    }

    // ---------- recuperar y listar ----------

    @Test
    void recuperar_vuelveAPonerloActivo() {
        producto.setEstado(ELIMINADO);

        useCase.recuperar(ID_PRODUCTO);

        assertThat(producto.getEstado()).isEqualTo(ACTIVO);
    }

    @Test
    void losListadosSeparanActivosDeEliminados() {
        when(repositorio.listarTodos()).thenReturn(List.of(
                producto(1, "ARROZ", "P-1", ACTIVO),
                producto(2, "AZUCAR", "P-2", ELIMINADO),
                producto(3, "SAL", "P-3", null)));

        assertThat(useCase.listarActivos()).extracting(Producto::getNombreProducto)
                .containsExactly("ARROZ", "SAL");
        assertThat(useCase.listarEliminados()).extracting(Producto::getNombreProducto)
                .containsExactly("AZUCAR");
        assertThat(useCase.listarTodos()).hasSize(3);
    }

    // ---------- guardar ----------

    @Test
    void unProductoNuevoNaceActivo() {
        when(repositorio.listarTodos()).thenReturn(List.of());
        Producto nuevo = producto(null, "SAL", "P-9", null);

        useCase.guardar(nuevo);

        assertThat(nuevo.getEstado()).isEqualTo(ACTIVO);
    }

    @Test
    void editarUnProductoNoLoRevive() {
        producto.setEstado(ELIMINADO);
        when(repositorio.listarTodos()).thenReturn(List.of(producto));
        Producto editado = producto(ID_PRODUCTO, "ARROZ", "PRD-001", null);

        useCase.guardar(editado);

        assertThat(editado.getEstado()).isEqualTo(ELIMINADO);
    }

    @Test
    void siElNombreLoTieneUnEliminado_seAvisaDondeEsta() {
        when(repositorio.listarTodos())
                .thenReturn(List.of(producto(99, "ARROZ", "P-99", ELIMINADO)));

        assertThatThrownBy(() -> useCase.guardar(producto(null, "ARROZ", "P-NUEVO", null)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("productos eliminados");
    }
}
