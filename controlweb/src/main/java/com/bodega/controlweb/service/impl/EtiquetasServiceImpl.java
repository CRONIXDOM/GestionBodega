package com.bodega.controlweb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bodega.controlweb.model.dto.response.DetalleEntregaResponseDto;
import com.bodega.controlweb.model.dto.response.DetalleSolicitudResponseDto;
import com.bodega.controlweb.model.dto.response.ProductoResponseDto;
import com.bodega.controlweb.model.dto.response.RolResponseDto;
import com.bodega.controlweb.model.dto.response.SolicitudResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioRolResponseDto;
import com.bodega.controlweb.service.IDetalleEntregaService;
import com.bodega.controlweb.service.IDetalleSolicitudService;
import com.bodega.controlweb.service.IEtiquetasService;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.service.ISolicitudService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.service.IUsuarioService;

@Service
public class EtiquetasServiceImpl implements IEtiquetasService {

    private final IUsuarioService servicioUsuario;
    private final IRolService servicioRol;
    private final IUsuarioRolService servicioUsuarioRol;
    private final IProductoService servicioProducto;
    private final ISolicitudService servicioSolicitud;
    private final IDetalleSolicitudService servicioDetalleSolicitud;
    private final IDetalleEntregaService servicioDetalleEntrega;

    public EtiquetasServiceImpl(IUsuarioService servicioUsuario, IRolService servicioRol,
            IUsuarioRolService servicioUsuarioRol, IProductoService servicioProducto,
            ISolicitudService servicioSolicitud, IDetalleSolicitudService servicioDetalleSolicitud,
            IDetalleEntregaService servicioDetalleEntrega) {
        this.servicioUsuario = servicioUsuario;
        this.servicioRol = servicioRol;
        this.servicioUsuarioRol = servicioUsuarioRol;
        this.servicioProducto = servicioProducto;
        this.servicioSolicitud = servicioSolicitud;
        this.servicioDetalleSolicitud = servicioDetalleSolicitud;
        this.servicioDetalleEntrega = servicioDetalleEntrega;
    }

    @Override
    public Map<Integer, String> usuariosConRol() {
        Map<Integer, String> nombres = new HashMap<>();
        for (UsuarioResponseDto u : servicioUsuario.listarUsuario()) {
            String completo = ((u.getNombreUsuario() == null ? "" : u.getNombreUsuario()) + " "
                    + (u.getApellidoUsuario() == null ? "" : u.getApellidoUsuario())).trim();
            nombres.put(u.getIdUsuario(), completo);
        }
        Map<Integer, String> roles = new HashMap<>();
        for (RolResponseDto r : servicioRol.listarRol()) {
            roles.put(r.getIdRol(), r.getNombreRol());
        }

        Map<Integer, String> resultado = new HashMap<>();
        for (UsuarioRolResponseDto ur : servicioUsuarioRol.listarUsuarioRol()) {
            String usuario = nombres.getOrDefault(ur.getIdUsuario(), "Usuario #" + ur.getIdUsuario());
            String rol = roles.get(ur.getIdRol());
            resultado.put(ur.getIdUsuarioRol(), rol == null ? usuario : usuario + " (" + rol + ")");
        }
        return resultado;
    }

    @Override
    public Map<Integer, ProductoResponseDto> productosPorId() {
        Map<Integer, ProductoResponseDto> resultado = new HashMap<>();
        for (ProductoResponseDto p : servicioProducto.listarProducto()) {
            resultado.put(p.getIdProducto(), p);
        }
        return resultado;
    }

    @Override
    public Map<Integer, Integer> solicitudPorEntrega() {
        // la entrega no guarda la solicitud: el vínculo va por sus líneas de detalle,
        // que apuntan al detalle de solicitud y este a la solicitud.
        Map<Integer, Integer> solicitudPorDetalle = new HashMap<>();
        for (DetalleSolicitudResponseDto d : servicioDetalleSolicitud.listarDetalleSolicitud()) {
            solicitudPorDetalle.put(d.getIdDetalleSolicitud(), d.getIdSolicitud());
        }

        Map<Integer, Integer> resultado = new HashMap<>();
        for (DetalleEntregaResponseDto de : servicioDetalleEntrega.listarDetalleEntrega()) {
            if (de.getIdEntrega() == null || de.getIdDetalleSolicitud() == null) {
                continue;
            }
            Integer idSolicitud = solicitudPorDetalle.get(de.getIdDetalleSolicitud());
            if (idSolicitud != null) {
                resultado.putIfAbsent(de.getIdEntrega(), idSolicitud);
            }
        }
        return resultado;
    }

    @Override
    public Map<Integer, String> solicitantePorSolicitud() {
        Map<Integer, String> porUsuarioRol = usuariosConRol();
        Map<Integer, String> resultado = new HashMap<>();
        for (SolicitudResponseDto s : servicioSolicitud.listarSolicitud()) {
            resultado.put(s.getIdSolicitud(), porUsuarioRol.get(s.getIdUsuarioRol()));
        }
        return resultado;
    }
}
