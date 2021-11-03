package com.ProyectoPersonas.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ProyectoPersonas.demo.excepciones.WebException;
import com.ProyectoPersonas.demo.servicios.UsuarioServicio;

@Controller
@RequestMapping("/registro")
public class RegistroController {
@Autowired
private UsuarioServicio usuarioservicio;
@GetMapping("") //"" significa que toma /registro

public String registro() {
	return "registro"; //devuelve el html
}
@PostMapping("")
public String registroSave(Model model,@RequestParam String username,@RequestParam String password, @RequestParam String password2,@RequestParam String dni  ) {
	try {
		usuarioservicio.save(username, password, password2,dni);
	} catch (WebException e) {
		model.addAttribute("error",e.getMessage()); //SE PASA A LA VISTA LOS ERRORES QUE SE PRODUCEN EN SAVE
		model.addAttribute("username",username);
		return "registro";
	}
	return "redirect:/"; //redirecciona a la vista de inicio
}

}
