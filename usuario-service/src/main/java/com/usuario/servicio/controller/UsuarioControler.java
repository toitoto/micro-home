package com.usuario.servicio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.servicio.entity.Usuario;
import com.usuario.servicio.models.Carro;
import com.usuario.servicio.models.Moto;
import com.usuario.servicio.service.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.getAll();
		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}

	@CircuitBreaker(name = "carroCB", fallbackMethod = "fallBackGetCarro")
	@GetMapping("/carro/{usuarioId}")
	public ResponseEntity<List<Object>> getCarros(@PathVariable("usuarioId") int usuarioId) {
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Object> carritos = usuarioService.getCarros(usuarioId);
		if (carritos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carritos);
	}

	@CircuitBreaker(name = "motoCB", fallbackMethod = "fallBackGetMoto")
	@GetMapping("/moto/{usuarioId}")
	public ResponseEntity<List<Object>> getMotos(@PathVariable("usuarioId") int usuarioId) {
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Object> motos = usuarioService.getMotos(usuarioId);
		if (motos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(motos);
	}

	@CircuitBreaker(name = "carroCB", fallbackMethod = "fallBackSaveCarro")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro) {
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
	}

	@CircuitBreaker(name = "motoCB", fallbackMethod = "fallBackSaveMoto")
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto) {
		Moto nuevoMoto = usuarioService.saveMoto(usuarioId, moto);
		return ResponseEntity.ok(nuevoMoto);
	}

	@CircuitBreaker(name = "todoCB", fallbackMethod = "fallBackGetTodo")
	@GetMapping("/todo/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listartodo(@PathVariable("usuarioId") int usuarioId) {
		Map<String, Object> resultado = usuarioService.getVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}

	private ResponseEntity<List<Carro>> fallBackGetCarro(@PathVariable("usuarioId") int id, RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + id + ", tiene los carros en el taller", HttpStatus.OK);
	}

	private ResponseEntity<List<Carro>> fallBackSaveCarro(@PathVariable("usuarioId") int id,@RequestBody Carro carro, RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + id + ", no tiene dinero para los carros", HttpStatus.OK);
	}

	private ResponseEntity<List<Moto>> fallBackGetMoto(@PathVariable("usuarioId") int id, RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + id + ", tiene las motos en el taller", HttpStatus.OK);
	}

	private ResponseEntity<List<Moto>> fallBackSaveMoto(@PathVariable("usuarioId") int id,@RequestBody Moto moto, RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + id + ", no tiene dinero para las motos", HttpStatus.OK);
	}

	private ResponseEntity<List<Moto>> fallBackGetTodo(@PathVariable("usuarioId") int id, RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + id + ", tiene los vehiculos en el taller", HttpStatus.OK);
	}
	
}
