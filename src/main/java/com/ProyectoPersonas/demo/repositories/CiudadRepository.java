package com.ProyectoPersonas.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ProyectoPersonas.demo.entidades.Ciudad;


@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, String>{
	@Query("Select p from Ciudad p where p.nombre LIKE :q") //LIKE PERMITE BUSCAR UN TEXTO LETRA POR LETRA
	 List<Ciudad> findAll(@Param("q") String q);
}
