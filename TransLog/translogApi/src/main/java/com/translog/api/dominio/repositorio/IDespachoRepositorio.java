package com.translog.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Despacho;

public interface IDespachoRepositorio {

	Despacho guardar(Despacho nuevoDespacho);

	Optional<Despacho> buscarPorId(int idDespacho);

	List<Despacho> listarTodos();

	void eliminar(int idDespacho);

}
