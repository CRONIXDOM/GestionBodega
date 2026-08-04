package com.bodega.controlweb.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.UsuarioRolRequestDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;
import com.bodega.controlweb.model.dto.response.RolResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioRolResponseDto;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.service.IUsuarioService;

@Service
public class UsuarioRolServiceImpl implements IUsuarioRolService {

    private final WebClient webCliente;

    @Autowired
    private IUsuarioService servicioUsuario;
    @Autowired
    private IRolService servicioRol;

    public UsuarioRolServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<UsuarioRolResponseDto> listarUsuarioRol() {
        return webCliente.get().uri("/usuarioRol").retrieve()
                .bodyToFlux(UsuarioRolResponseDto.class).collectList().block();
    }

    @Override
    public void guardarUsuarioRol(UsuarioRolRequestDto nuevo) {
        webCliente.post().uri("/usuarioRol").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public UsuarioRolResponseDto buscarUsuarioRolId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/usuarioRol/buscarId/{id}").build(id))
                .retrieve().bodyToMono(UsuarioRolResponseDto.class).block();
    }

    @Override
    public void eliminarUsuarioRol(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/usuarioRol/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        Map<Integer, UsuarioResponseDto> usuariosPorId = servicioUsuario.listarUsuario().stream()
                .collect(java.util.stream.Collectors.toMap(UsuarioResponseDto::getIdUsuario, Function.identity()));
        Map<Integer, RolResponseDto> rolesPorId = servicioRol.listarRol().stream()
                .collect(java.util.stream.Collectors.toMap(RolResponseDto::getIdRol, Function.identity()));

        return listarUsuarioRol().stream()
                .map(ur -> {
                    UsuarioResponseDto usuario = usuariosPorId.get(ur.getIdUsuario());
                    RolResponseDto rol = rolesPorId.get(ur.getIdRol());
                    String nombreUsuario = usuario != null
                            ? usuario.getNombreUsuario() + " " + usuario.getApellidoUsuario()
                            : "Usuario #" + ur.getIdUsuario();
                    String nombreRol = rol != null ? rol.getNombreRol() : "Rol #" + ur.getIdRol();
                    return new OpcionSelectDto(ur.getIdUsuarioRol(), nombreUsuario + " → " + nombreRol);
                })
                .toList();
    }
}
