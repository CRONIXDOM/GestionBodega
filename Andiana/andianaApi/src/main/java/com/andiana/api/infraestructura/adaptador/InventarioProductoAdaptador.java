package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.InventarioProducto;
import com.andiana.api.dominio.puerto.InventarioProductoRepositorio;
import com.andiana.api.infraestructura.entidad.InventarioProductoEntidad;
import com.andiana.api.infraestructura.jpa.InventarioProductoJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.InventarioProductoMapeador;

@Repository
public class InventarioProductoAdaptador extends AdaptadorCrud<InventarioProducto, InventarioProductoEntidad>
		implements InventarioProductoRepositorio {

	private final InventarioProductoJpaRepositorio jpaInventarioProducto;

	public InventarioProductoAdaptador(InventarioProductoJpaRepositorio jpaInventarioProducto) {
		super(jpaInventarioProducto, InventarioProductoMapeador::aDominio, InventarioProductoMapeador::aEntidad);
		this.jpaInventarioProducto = jpaInventarioProducto;
	}

	@Override
	public List<InventarioProducto> buscarPorLote(Integer idLote) {
		return convertir(jpaInventarioProducto.findByIdLote(idLote));
	}
}
