package com.ProyectoPersonas.demo.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
//genera una tabla por cada entidad, pero en la tabla padre y la tabla id tienen un id q son iguales (relacionadas)
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
	@Id
	@GeneratedValue(generator ="uuid")
	@GenericGenerator(name ="uuid",strategy="uuid2")
	public String id;
	private String dni;
    private String nombre;
    private String apellido;
    private Integer edad;
    @ManyToOne //De muchos a uno. Muchas personas pueden tener la misma ciudad
    private Ciudad ciudad;

    public Persona() {
    }
    

    public Persona(String dni,String nombre, String apellido, Integer edad) {
        this.dni = dni;
    	this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }
    
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public Ciudad getCiudad() {
    	return ciudad;
    }
    
    public void setCiudad(Ciudad ciudad) {
    	this.ciudad = ciudad;
    }
    
}


