package com.ProyectoPersonas.demo.entidades;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ProyectoPersonas.demo.enums.Role;


@Entity

public class Usuario extends Persona{ //en la base de datos no esta soportado de manera nativa (la herencia)
	//como tiene el mismo id que persona no ponemos id
private String username;
private String password;
@Enumerated(EnumType.STRING) //COMO SE GUARDA EN BASE DE DATOS (ORDINAL NUMERO Y STRING )
private Role rol;

public Usuario() {
	super();
}
public Usuario(String username, String password) {
	super();
	this.username = username;
	this.password = password;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

public Role getRol() {
	return rol;
}
public void setRol(Role rol) {
	this.rol = rol;
}
@Override
public String toString() {
	return "Usuario [username=" + username + ", password=" + password + "]";
}

}
