package com.moto.servicio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moto.servicio.entity.Moto;
import com.moto.servicio.repository.MotoRepository;

@Service
public class MotoService {
	
	@Autowired
	private MotoRepository motoRepository;
	

	public List<Moto> getAll(){		
		return motoRepository.findAll();
	}
	
	public Moto getMotoById(int id){		
		return motoRepository.findById(id).orElse(null);		
	}
	
	public List<Moto> findmotoByUsuario(int usuarioId){		
		return motoRepository.findByUsuarioId(usuarioId);		
	}
	
	public Moto save(Moto moto){
		Moto nuevaMoto=motoRepository.save(moto);
		return nuevaMoto;		
	}
	
	
	
}
