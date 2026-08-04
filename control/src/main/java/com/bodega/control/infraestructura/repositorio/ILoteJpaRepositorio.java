package com.bodega.control.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.LoteEntity;

public interface ILoteJpaRepositorio extends JpaRepository<LoteEntity, Integer> {

    List<LoteEntity> findByNumeroLote(String numeroLote);

    @Query("Select lot from LoteEntity lot")
    List<LoteEntity> listarLotes();

    @Query("Select lot from LoteEntity lot where lot.numeroLote=?1")
    List<LoteEntity> buscarLoteNumero(String numeroLote);

    @Query("Select lot from LoteEntity lot where lot.fechaVencimiento=?1")
    List<LoteEntity> buscarLoteFechaVencimiento(LocalDate fechaVencimiento);

    @Query("Select lot from LoteEntity lot where lot.producto.idProducto=?1 order by lot.fechaIngreso asc, lot.idLote asc")
    List<LoteEntity> buscarPorProductoOrdenadoFifo(int idProducto);

}