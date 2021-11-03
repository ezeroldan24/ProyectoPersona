package com.ProyectoPersonas.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ProyectoPersonas.demo.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioServicio usuar;
	@GetMapping("/list")
	public String listarUsuarios(Model modelo,@RequestParam(required =false) String q) { //Model para pasar esto a la vista(la lista)
		if(q!=null) {
			modelo.addAttribute("usuarios",usuar.listAllByQ(q));
		}else {
		modelo.addAttribute("usuarios",usuar.listAll()); //"persona" es como se va llamar en la vista html y al lado la lista del servicio
		}
		return "usuario-list";	
	}

}
