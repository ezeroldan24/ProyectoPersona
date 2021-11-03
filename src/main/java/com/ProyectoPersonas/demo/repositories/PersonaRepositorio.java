package com.ProyectoPersonas.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ProyectoPersonas.demo.entidades.Persona;
@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, String>{
 @Query("Select p from Persona p where p.nombre LIKE :q or p.apellido LIKE :q or p.edad LIKE :q or p.ciudad.nombre LIKE :q") //LIKE PERMITE BUSCAR UN TEXTO LETRA POR LETRA y tambien por nombre de ciudad
 List<Persona> findAllByQ(@Param("q") String q);
 
 @Query("Select p from Persona p where p.ciudad.nombre =:q") //TRAE LA LISTA DE PERSONAS QUE PERTENECEN A CIUDAD
 List<Persona> findAllByCiudad(@Param("q") String q);
 
 @Query("Select p from Persona p where p.dni =:q") //TRAE LA LISTA DE PERSONAS QUE PERTENECEN A CIUDAD
 Persona findAllByDni(@Param("q") String q);
}
