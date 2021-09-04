package br.com.texoit.services.interfaces;

import java.util.List;

import br.com.texoit.dtos.FilmeDTO;
import br.com.texoit.dtos.IntervaloDTO;
import br.com.texoit.dtos.IntervaloPremiacaoDTO;

public interface FilmeService {

	public List<FilmeDTO> findAll();

	public FilmeDTO save(FilmeDTO filme) throws Exception;

	public IntervaloDTO listarIntervaloPremio();

	public void delete(Long id) throws Exception;
	
	public FilmeDTO update(FilmeDTO filme) throws Exception;
	
	public IntervaloPremiacaoDTO buscarProdutorComMaiorIntervalo();
	
	public IntervaloPremiacaoDTO buscarProdutorComPremioMaisRapido();
	

}
