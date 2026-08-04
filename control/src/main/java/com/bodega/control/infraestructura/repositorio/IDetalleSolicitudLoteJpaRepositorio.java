package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.DetalleSolicitudLoteEntity;

public interface IDetalleSolicitudLoteJpaRepositorio extends JpaRepository<DetalleSolicitudLoteEntity, Integer> {

	@Query("Select asig from DetalleSolicitudLoteEntity asig where asig.detalleSolicitud.idDetalleSolicitud=?1")
	List<DetalleSolicitudLoteEntity> buscarPorDetalleSolicitud(int idDetalleSolicitud);

}
