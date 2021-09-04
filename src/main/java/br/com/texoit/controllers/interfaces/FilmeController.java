package br.com.texoit.controllers.interfaces;

import org.springframework.http.ResponseEntity;

import br.com.texoit.dtos.FilmeDTO;

public interface FilmeController {

	public ResponseEntity<?> findAll();

	public ResponseEntity<?> save(FilmeDTO filme);
	
	public ResponseEntity<?> update(FilmeDTO filmeDTO, Long id);

	public ResponseEntity<?> listarIntervaloPremio();

	public ResponseEntity<?> delete(Long id);
	
}
