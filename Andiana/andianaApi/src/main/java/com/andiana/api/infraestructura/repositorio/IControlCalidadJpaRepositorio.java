package com.andiana.api.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.ControlCalidadEntity;

public interface IControlCalidadJpaRepositorio extends JpaRepository<ControlCalidadEntity, Integer> {

	List<ControlCalidadEntity> findByIdLoteOrderByFechaControlDescIdControlDesc(int idLote);
}
