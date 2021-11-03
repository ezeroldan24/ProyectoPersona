package com.ProyectoPersonas.demo.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProyectoPersonas.demo.entidades.Ciudad;

import com.ProyectoPersonas.demo.excepciones.WebException;
import com.ProyectoPersonas.demo.repositories.CiudadRepository;

@Service
public class CiudadServicio {
	@Autowired
	private PersonaServicio pers; //LOS UNICOS Q TIENEN ACCESO AL RESOSITORIO DE SU ENTIDAD, SON LOS SERVICIOS DE SU ENTIDAD
@Autowired
private CiudadRepository ciudadRepository;
public Ciudad save(String name) {
	Ciudad ciudad = new Ciudad();
	ciudad.setNombre(name);
	return ciudadRepository.save(ciudad);
}

public Ciudad save(Ciudad ciudad) throws WebException {
	if(ciudad.getNombre().isEmpty() || ciudad.getNombre()==null) {
		throw new WebException("El nombre de la ciudad no puede ser nulo");
	}
	return ciudadRepository.save(ciudad);
}

//public Ciudad saveByPersona(Ciudad ciudad) throws WebException { //VALIDACION SI ESTA LA CIUDAD
	//if(ciudad.getId().isEmpty() || ciudad.getId()==null) {
	//	throw new WebException("Ocurrio un error al querer guardar la Ciudad");
	//}else {
	//	Optional <Ciudad> optional=ciudadRepository.findById(ciudad.getId());
	//	if(optional.isPresent()) {
	//		ciudad = optional.get();
	//}
	//}
	//return save(ciudad);
//}
//listar CIUDAD
	public List<Ciudad> listAll(){
		return ciudadRepository.findAll();
	}
	
	//buscamos la CIUDAD
		public Ciudad findById(Ciudad ciudad){
			Optional <Ciudad> optional=ciudadRepository.findById(ciudad.getId());
			if(optional.isPresent()) {
				ciudad = optional.get();
		}
			return ciudad;
		}
	
	//Ver ciudades por letras relacionadas
		public List<Ciudad> listAllByQ(String q){
			return ciudadRepository.findAll("%"+q+"%"); // CUALQUIER COINCIDENCIA QUE TENGA DENTRO LAS LETRAS
		}
		
		
	@Transactional
	//Eliminar persona
	public void delete(Ciudad p) {
		
		ciudadRepository.delete(p);
	}
	
	
	@Transactional
	public void deleteById(String id) {
	Optional<Ciudad> optional = ciudadRepository.findById(id);//EL OPTIONAL PUEDE RECIBIR UN OBJETO NULO VALIDACION
	if(optional.isPresent()) { //SI OPTIONAL ESTA PRESENTE Y NO ES NULO
		Ciudad ciudad = optional.get(); 
		pers.deleteFieldCiudad(ciudad);
		ciudadRepository.delete(ciudad); //SI ESTA PRESENTE LA CIUDAD LA BORRA
	}
		
	
}
	//Editar cIUDAD
			public Optional<Ciudad> findById(String id){ //BUSCAMOS UNA PERSONA Y DEVOLVEMOS UN OPTIONAL
				return ciudadRepository.findById(id);
			}
}

