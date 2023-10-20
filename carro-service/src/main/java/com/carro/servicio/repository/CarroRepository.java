package com.carro.servicio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carro.servicio.entity.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer> {

	List<Carro> findByUsuarioId(int id);
}
