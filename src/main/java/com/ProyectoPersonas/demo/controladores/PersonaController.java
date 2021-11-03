package com.ProyectoPersonas.demo.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ProyectoPersonas.demo.entidades.Persona;
import com.ProyectoPersonas.demo.servicios.CiudadServicio;
import com.ProyectoPersonas.demo.servicios.PersonaServicio;

@Controller
@RequestMapping("/persona")
public class PersonaController {
	@Autowired //Intancia el objeto de abajo
	private PersonaServicio pers;
	@Autowired //Intancia el objeto de abajo
	private CiudadServicio ciud;
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')") //ESTO ES PARA QUE CUANDO ENTREN A LA URL
@GetMapping("/list")
public String listarPersonas(Model modelo,@RequestParam(required =false) String q) { //Model para pasar esto a la vista(la lista) /persona/list no puedan entrar si no son ADMIN
	if(q!=null) {
		modelo.addAttribute("personas",pers.listAllByQ(q));
	}else {
	modelo.addAttribute("personas",pers.listAll()); //"persona" es como se va llamar en la vista html y al lado la lista del servicio
	}
	return "persona-list";	
}

@GetMapping("/delete")
public String eliminarPersona(@RequestParam(required=true)String id) { //SI O SI ES REQUERIDO EL ID
pers.deleteById(id);
return "redirect:/persona/list";
}

@GetMapping("/form")
public String crearPersona(Model modelo,@RequestParam(required=false)String id) {
	if(id != null) {
		Optional<Persona> optional = pers.findById(id); //la busca por id
		if(optional.isPresent()) {//si persona esta presente
			modelo.addAttribute("persona",optional.get()); //modelo
		}else {
			return "redirect:/persona/list";
		}
	}else {
		modelo.addAttribute("persona",new Persona()); //Si el id es nulo se agrega una nueva persona a cargar
	}
	modelo.addAttribute("ciudades",ciud.listAll()); //lista de ciudades
	return "persona-form";
}


@PostMapping("/save") // A la url donde va a ir
public String guardarPersona(Model modelo,RedirectAttributes redirectAttributes,@ModelAttribute Persona persona) {
	try {
		pers.save(persona);
		redirectAttributes.addFlashAttribute("sucess", "Persona guardada con éxito");
	}catch(Exception e) {
		modelo.addAttribute("error",e.getMessage());
		modelo.addAttribute("persona",persona); //e.getMessage para tener el mensaje del error, esto se manda a la vista
		modelo.addAttribute("ciudades",ciud.listAll());
	redirectAttributes.addFlashAttribute("error", e.getMessage()); //Manda el error a la sigueinte direccion lista (ya que cambia de model en lista)
	}
	return "redirect:/persona/list"; //cuando se lance el error va a ir a persona list
}
}

