package br.com.texoit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.texoit.controllers.interfaces.FilmeController;
import br.com.texoit.dtos.FilmeDTO;
import br.com.texoit.services.FilmeServiceImpl;

@RestController
@RequestMapping("/filme")
public class FilmeControllerImpl implements FilmeController{

	@Autowired
	private FilmeServiceImpl filmeServiceImpl;

	@Override
	@GetMapping
	public ResponseEntity<?> findAll() {
		
		return ResponseEntity.ok(filmeServiceImpl.findAll());
		
	}

	@Override
	@PostMapping
	public ResponseEntity<?> save(@RequestBody FilmeDTO filme) {
		
		try {

			return ResponseEntity.status(HttpStatus.CREATED).body(filmeServiceImpl.save(filme));
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
		
	}

	@Override
	@GetMapping("/awards")
	public ResponseEntity<?> listarIntervaloPremio() {
		
		try {

			return ResponseEntity.status(HttpStatus.OK).body(filmeServiceImpl.listarIntervaloPremio());
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
		
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		try {
			
			filmeServiceImpl.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
		
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody FilmeDTO filmeDTO, @PathVariable Long id) {
		
		try {
			//forca update do objeto pelo path
			filmeDTO.setId(id);
			filmeServiceImpl.save(filmeDTO);
			
			return ResponseEntity.ok().body(filmeServiceImpl.save(filmeDTO));
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
		
	}
	
}
