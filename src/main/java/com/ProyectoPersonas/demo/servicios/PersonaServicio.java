package com.ProyectoPersonas.demo.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProyectoPersonas.demo.entidades.Ciudad;
import com.ProyectoPersonas.demo.entidades.Persona;
import com.ProyectoPersonas.demo.excepciones.WebException;
import com.ProyectoPersonas.demo.repositories.PersonaRepositorio;


@Service
public class PersonaServicio {

	@Autowired //INYECCION DE DEPENDENCIA, SE INYECTA LA CLASE PARA USARLA
private PersonaRepositorio pers;
	@Autowired //PERSISIENDO LA ENTIDAD CIUDAD
	private CiudadServicio ciud;
	
	

	//guardar persona
	@Transactional
	public Persona save(Persona p) throws WebException {
		
		if(findByDni(p.getDni()) !=null) { //si existe el dni de la persona, no te deja hacerlo
			throw new WebException("ERROR: 'No se puede registrar una persona con un DNI que ya existe'");
		}
		if(p.getNombre().isEmpty() || p.getNombre() == null || p.getNombre().length()< 3) {
			throw new WebException("ERROR: 'El nombre no puede estar vacío o tener menos de 3 caracteres'");
		}
		if(p.getApellido().isEmpty() || p.getApellido() == null) {
			throw new WebException("ERROR: 'El Apellido no puede estar vacío'");
		}
		if(p.getEdad()==null|| p.getEdad() < 1) {
			throw new WebException("ERROR: 'La edad no puede estar vacío o ser menor a 1'");
		}
		if(p.getDni()==null|| p.getDni().isEmpty()) {
			throw new WebException("ERROR: 'El dni no puede estar vacío'");
		}
		if(p.getCiudad() == null) {
			throw new WebException("ERROR: 'La ciudad no puede estar vacia'");
		}else { //PARA GUARDARLA COMO ES UNA ENTIDAD TENEMOS QUE PERSISTIRLA ANTES EN LA BASE DE DATOS (LLAMANDO A SU SERVICIO)
			p.setCiudad(ciud.findById(p.getCiudad())); //SE GUARDA LA CIUDAD EN EL ATRIBUTO CIUDAD DE PERSONA
		}
	return pers.save(p);
	}
	
	//ESTE NO SE USA, USAMOS EL DE ARRIBA
	@Transactional //Transactional (se pone porque cambia algo en la base de datos)
	public Persona save(String nombre,String apellido, Integer edad) {
		Persona p = new Persona();
		p.setNombre(nombre);
		p.setApellido(apellido);
		p.setEdad(edad);
		return pers.save(p);
	}
	
	//listar personas
	public List<Persona> listAll(){
		return pers.findAll();
	}
	
	
	//listar por ciudad
		public List<Persona> listAllByCiudad(String nombre){
			return pers.findAllByCiudad(nombre);
		}
	//listar personas por escritura
		public List<Persona> listAllByQ(String q){
			return pers.findAllByQ("%"+q+"%"); // CUALQUIER COINCIDENCIA QUE TENGA DENTRO LAS LETRAS
		}

	
	//listar personas por escritura
		public List<Persona> listAllCiudad(String nombre){
			return pers.findAllByCiudad(nombre); 
		}
	
		@Transactional
	//Eliminar persona
	public void delete(Persona p) {
		pers.delete(p);
	}
	@Transactional
	
	//Eliminar por ciudad
	public void deleteFieldCiudad(Ciudad ciudad) {
		List<Persona> personas = listAllByCiudad(ciudad.getNombre()); //busca todas las personas que tienen esta ciudad
		for(Persona persona : personas) { 
			persona.setCiudad(null); //pone null
			
		}
		pers.saveAll(personas); // ya no tiene el campo del id de ciudad
	}
	
	@Transactional
	public void deleteById(String id) {
	Optional<Persona> optional = pers.findById(id);//EL OPTIONAL PUEDE RECIBIR UN OBJETO NULO VALIDACION
	if(optional.isPresent()) { //SI OPTIONAL ESTA PRESENTE Y NO ES NULO
		pers.delete(optional.get()); //SI ESTA PRESENTE LA PERSONA LA BORRA
	}
		
	
}
	//Editar ciudad
			public Optional<Persona> findById(String id) { //BUSCAMOS UNA PERSONA Y DEVOLVEMOS UN OPTIONAL
				return pers.findById(id);
			}

//Editar ciudad
public Persona findByDni(String dni) { //BUSCAMOS UNA PERSONA Y DEVOLVEMOS UN OPTIONAL
	return pers.findAllByDni(dni);
}
}

