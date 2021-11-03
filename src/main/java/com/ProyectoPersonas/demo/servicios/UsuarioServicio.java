package com.ProyectoPersonas.demo.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ProyectoPersonas.demo.entidades.Persona;
import com.ProyectoPersonas.demo.entidades.Usuario;
import com.ProyectoPersonas.demo.enums.Role;
import com.ProyectoPersonas.demo.excepciones.WebException;
import com.ProyectoPersonas.demo.repositories.UsuarioRepository;

@Service
public class UsuarioServicio implements UserDetailsService{
@Autowired
private UsuarioRepository usuario;
@Autowired
private PersonaServicio pers; //SIEMPRE SE LLAMA AL SERVICIO, DESDE EL SERVICIO PODES LLAMAR AL REPOSITORIO

@Transactional
public Usuario save(String username,String password,String password2,String dni) throws WebException {// como parametro username y las dos contraseñas para validar la primera
	Usuario usuario2 = new Usuario();
	if(dni == null || dni.isEmpty()) { 
		throw new WebException("ERROR: 'El dni del usuario no puede estar vacío'");
	}
	Persona persona = pers.findByDni(dni); //PARA PODER USAR LA PERSONA
	if(persona == null) { 
		throw new WebException("ERROR: 'No se puede registrar un usuario con un DNI que no exista en la base de datos'");
	}
	if(username.isEmpty() || username == null) {
		throw new WebException("El nombre de usuario no puede estar vacio");
	}
	if(findByUsername(username) != null) { 
		throw new WebException("ERROR: 'El nombre de usuario ya esta registrado intente con otro'");
	}
	if(password.isEmpty() || password == null || password2.isEmpty() || password2 == null) {
		throw new WebException("El contraseña no puede estar vacia");
	}
	
	if(!password.equals(password2)) {
		throw new WebException("Las contraseñas deben ser iguales");
	}
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //INICIALIZACION PARA ENCRIPTAR LA CONTRASEÑA
	usuario2.setId(persona.getId());
	usuario2.setNombre(persona.getNombre());
	usuario2.setApellido(persona.getApellido());
	usuario2.setEdad(persona.getEdad());
	usuario2.setCiudad(persona.getCiudad());
	usuario2.setDni(persona.getId());
	usuario2.setUsername(username);
	usuario2.setPassword(encoder.encode(password)); //ENCODE SIRVE PARA ENCRIPTAR LA CONTRASEÑA
	usuario2.setRol(Role.USER); //CUALQUIER USUARIO QUE SE REGISTRE ES USER
	pers.delete(persona);
	return usuario.save(usuario2); //SE GUARDA LA PERSONA CON SU USUARIO
}

public Usuario findByUsername(String username) {
	return usuario.findByUsername(username);
}

//listar personas
public List<Usuario> listAll(){
	return usuario.findAll();
}

public List<Usuario> listAllByQ(String q){
//	return usuario.findAllByQ("%"+q+"%"); // CUALQUIER COINCIDENCIA QUE TENGA DENTRO LAS LETRAS
	return usuario.findAll();
}

//Esto se implementa cuando un usuario se quiera logear (seguridad)
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//permiso cliente
	try {
		Usuario usua = usuario.findByUsername(username); //RECIBE EL NOMBRE DE USUARIO DE LA PERSONA
		User user;
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+usua.getRol())); //nombre de la lista, add y Simple clase que implemente GrantedAuthority (se realiza el premiso cliente) 
		return new User(username, usua.getPassword(), authorities); //el user implementa UserDetails
	}catch(Exception e) {
		throw new UsernameNotFoundException("El usuario no existe");
	}
}
}
