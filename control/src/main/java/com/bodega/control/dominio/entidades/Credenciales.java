package com.bodega.control.dominio.entidades;

public class Credenciales {
	private Integer idCredenciales;
	private String usuario;
	private String correo;
	private String contrasena;

	public Credenciales() {
	}

	public Credenciales(Integer idCredenciales, String usuario, String correo, String contrasena) {
		this.idCredenciales = idCredenciales;
		this.usuario = usuario;
		this.correo = correo;
		this.contrasena = contrasena;
	}

	public Integer getIdCredenciales() {
		return idCredenciales;
	}

	public void setIdCredenciales(Integer idCredenciales) {
		this.idCredenciales = idCredenciales;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
}
