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

import com.ProyectoPersonas.demo.entidades.Ciudad;

import com.ProyectoPersonas.demo.servicios.CiudadServicio;


@Controller
@RequestMapping("/ciudad")
public class CiudadController {
@Autowired //Intancia el objeto de abajo
private CiudadServicio pers;
@GetMapping("/list")
public String listarCiudades(Model modelo,@RequestParam(required =false) String q) { //Model para pasar esto a la vista(la lista)
	if(q!=null) {
		modelo.addAttribute("ciudades",pers.listAll());
	}else {
	modelo.addAttribute("ciudades",pers.listAll()); //"persona" es como se va llamar en la vista html y al lado la lista del servicio
	}
	return "ciudad-list";	
}

@GetMapping("/delete")
public String eliminarCiudad(@RequestParam(required=true)String id) { //SI O SI ES REQUERIDO EL ID
pers.deleteById(id);
return "redirect:/ciudad/list";
}

@GetMapping("/form")
public String crearCiudad(Model modelo,@RequestParam(required=false)String id) {
	if(id != null) {
		Optional<Ciudad> optional = pers.findById(id); //la busca por id
		if(optional.isPresent()) {//si persona esta presente
			modelo.addAttribute("ciudad",optional.get()); //modelo
		}else {
			return "redirect:/ciudad/list";
		}
	}else {
		modelo.addAttribute("ciudad",new Ciudad()); //Si el id es nulo se agrega una nueva persona a cargar
	}
	return "ciudad-form";
}

@PostMapping("/save") // A la url donde va a ir
public String guardarCiudad(Model modelo,RedirectAttributes redirectAttributes,@ModelAttribute Ciudad ciudad) {
	try {
		pers.save(ciudad);
		redirectAttributes.addFlashAttribute("sucess", "Ciudad guardada con éxito");
	}catch(Exception e) {
		modelo.addAttribute("error",e.getMessage()); //e.getMessage para tener el mensaje del error, esto se manda a la vista
	redirectAttributes.addFlashAttribute("error", e.getMessage()); //Manda el error a la sigueinte direccion lista (ya que cambia de model en lista)
	}
	return "redirect:/ciudad/list"; //cuando se lance el error va a ir a persona list
}
}


