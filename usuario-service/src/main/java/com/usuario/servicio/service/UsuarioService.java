package com.usuario.servicio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.servicio.entity.Usuario;
import com.usuario.servicio.feignclients.CarroFeignClient;
import com.usuario.servicio.feignclients.MotoFeignClient;
import com.usuario.servicio.models.Carro;
import com.usuario.servicio.models.Moto;
import com.usuario.servicio.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CarroFeignClient carroFeignClient;

	@Autowired
	private MotoFeignClient motoFeignClient;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Object> getCarros(int usuarioId){
		List<Object> carritos=restTemplate.getForObject("http://localhost:8082/carro/usuario/"+usuarioId, List.class);
		return carritos;
	}
	
	public List<Object> getMotos(int usuarioId){
		List<Object> motos=restTemplate.getForObject("http://localhost:8083/moto/usuario/"+usuarioId, List.class);
		return motos;
	}
	
	public Carro saveCarro(int usuarioId,Carro carro){
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro=carroFeignClient.save(carro) ;
		return nuevoCarro;
	}
	
	public Moto saveMoto(int usuarioId,Moto moto){
		moto.setUsuarioId(usuarioId);
		Moto nuevoMoto=motoFeignClient.save(moto) ;
		return nuevoMoto;
	}
	
		
	public List<Usuario> getAll(){
		return usuarioRepository.findAll();		
	}
	
	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario){
		Usuario nuevoUsuario= usuarioRepository.save(usuario);
		return nuevoUsuario;		
	}
	
	public Map<String, Object> getVehiculos(int usuarioId){
		Map<String, Object>resultado =new HashMap<>();
		Usuario usuario=usuarioRepository.findById(usuarioId).orElse(null);
		if(usuario==null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}
		resultado.put("Usuario", usuario);
		List<Carro> carros=carroFeignClient.getCarros(usuarioId);
		if(carros==null) {
			resultado.put("Carros", "El usuario no tiene Carros");
		}else {
			resultado.put("Carros", carros);			
		}
		List<Moto> motos=motoFeignClient.getMotos(usuarioId);
		if(motos==null) {
			resultado.put("Motos", "El usuario no tiene Motos");
		}else {
			resultado.put("Motos", motos);			
		}
		return resultado;
	}
	
	
	
}
