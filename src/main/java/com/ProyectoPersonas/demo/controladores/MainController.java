package com.ProyectoPersonas.demo.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ProyectoPersonas.demo.entidades.Persona;

@Controller
@RequestMapping("/")
public class MainController {
	List<Persona> personas = new ArrayList<>();
@GetMapping("")

public String HolaMundo(Model modelo) {
	modelo.addAttribute("tittle","Probando thymeleaf"); //como se llama en html, y lo que queres poner
	modelo.addAttribute("personas",personas);// se agrega al modelo para la vista
	return "index";
}

@GetMapping("/crearpersona")
public String crearPersona() {
	return "persona-form";
}

@PostMapping("/guardarpersona") //se encuentra en persona-form
public String guardar(@RequestParam String dni,@RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer edad) { //RequestParam para recibir los parametros
	
	Persona persona = new Persona (dni,nombre,apellido,edad);
	personas.add(persona);
	return "redirect:/";	
}
}

	

