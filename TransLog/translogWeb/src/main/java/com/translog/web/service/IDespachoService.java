package com.translog.web.service;

import java.util.List;

import com.translog.web.model.dto.request.DespachoRequestDto;
import com.translog.web.model.dto.response.DespachoResponseDto;
import com.translog.web.model.dto.response.EnvioResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;

public interface IDespachoService {

    List<DespachoResponseDto> listarDespacho();

    void guardarDespacho(DespachoRequestDto nuevo);

    DespachoResponseDto buscarDespachoId(Integer id);

    void eliminarDespacho(Integer id);

    List<OpcionSelectDto> listarOpciones();

    /** Los envios que lleva un despacho. */
    List<EnvioResponseDto> enviosDelDespacho(Integer idDespacho);

    /** Los envios que hoy se podrian cargar en esa ruta. */
    List<EnvioResponseDto> enviosDisponibles(Integer idRuta, Integer idDespacho);
}
