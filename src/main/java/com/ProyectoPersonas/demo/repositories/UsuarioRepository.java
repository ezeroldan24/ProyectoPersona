package com.ProyectoPersonas.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ProyectoPersonas.demo.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
@Query("Select u from Usuario u where u.username =:username") //todos los metodos de las interfaces son publicos
Usuario findByUsername(@Param("username") String username);

}
