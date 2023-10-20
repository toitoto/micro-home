package com.usuario.servicio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.servicio.entity.Usuario;
import com.usuario.servicio.models.Carro;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

}
