package br.com.texoit.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.texoit.dtos.FilmeDTO;
import br.com.texoit.dtos.IntervaloDTO;
import br.com.texoit.dtos.IntervaloPremiacaoDTO;
import br.com.texoit.models.Filme;
import br.com.texoit.respositories.FilmeRepository;
import br.com.texoit.services.interfaces.FilmeService;

@Service
public class FilmeServiceImpl implements FilmeService {

	@Autowired
	private FilmeRepository filmeRepository;

	@Override
	public List<FilmeDTO> findAll() {

		List<Filme> listaFilmes = filmeRepository.findAll();
		return FilmeDTO.converterLista(listaFilmes);

	}

	@Override
	public FilmeDTO save(FilmeDTO filme) throws Exception {

		validate(filme);

		Filme object = filmeRepository.saveAndFlush(FilmeDTO.converterToObject(filme));
		return filme = FilmeDTO.converterToDTO(object);

	}

	private void validate(FilmeDTO filme) throws Exception {

		if (filme.getYear() == null) {
			throw new Exception("Informe o Ano do Filme");
		} else {
			if (filme.getYear().toString().isEmpty() || filme.getYear().toString().length() < 4) {
				throw new Exception("Informe o Ano do Filme Valido. Verifique se o Ano informado é valido!(Ex:2021)");
			}
		}

		if (filme.getProducers() == null) {
			throw new Exception("Informe o(s) Produtor(es) do Filme");
		} else {
			if (filme.getProducers().toString().isEmpty()) {
				throw new Exception("Informe o(s) Produtor(es) do Filme");
			}
		}

		if (filme.getStudios() == null) {
			throw new Exception("Informe o Estudio do Filme");
		} else {
			if (filme.getStudios().toString().isEmpty()) {
				throw new Exception("Informe o Estudio do Filme");
			}
		}

		if (filme.getTitle() == null) {
			throw new Exception("Informe o Nome do Filme");
		} else {
			if (filme.getTitle().toString().isEmpty()) {
				throw new Exception("Informe o Nome do Filme");
			}
		}

		if (filme.getWinner() == null) {
			throw new Exception("Informe se o Filme foi Vencedor.(Valores aceitos: true/false)");
		} else {
			if (filme.getTitle().toString().isEmpty()) {
				throw new Exception("Informe se o Filme foi Vencedor.(Valores aceitos: true/false)");
			}
		}

	}

	@Override
	public IntervaloDTO listarIntervaloPremio() {

		IntervaloDTO intervalDTO = new IntervaloDTO();
		intervalDTO.setMax(buscarProdutorComMaiorIntervalo());
		intervalDTO.setMin(buscarProdutorComPremioMaisRapido());
		return intervalDTO;

	}

	@Override
	public void delete(Long id) throws Exception {

		Optional<Filme> optional = filmeRepository.findById(id);
		if (optional.isPresent()) {
			filmeRepository.deleteById(id);
		} else {
			throw new Exception("Filme não Encontrado");
		}

	}

	@Override
	public FilmeDTO update(FilmeDTO filme) throws Exception {

		validate(filme);

		Filme object = filmeRepository.saveAndFlush(FilmeDTO.converterToObject(filme));
		return filme = FilmeDTO.converterToDTO(object);

	}

	@Override
	public IntervaloPremiacaoDTO buscarProdutorComMaiorIntervalo() {

		List<IntervaloPremiacaoDTO> intervalsDTO = new ArrayList<>();
		
	    try {
	    	
	      List<Filme> Filmes = filmeRepository.findAll();
	      
	      if (Filmes.size() == 0) {
	    	  
	        return new IntervaloPremiacaoDTO();
	        
	      }
	      for (Filme Filme : Filmes) {
	        if (!Filme.getWinner()) {
	          continue; 
	        }
	        IntervaloPremiacaoDTO intervalo = new IntervaloPremiacaoDTO();
	        for (String produtor : this.getProducers(Filme)) {
	          intervalo.setProducer(produtor);
	          if (intervalsDTO.contains(intervalo)) {
	            int index = intervalsDTO.indexOf(intervalo);
	            Integer previousWin = intervalsDTO.get(index).getFollowingWin();
	            intervalsDTO.get(index).setFollowingWin(Filme.getYear());
	            intervalsDTO.get(index).setInterval(calculaIntervalo(intervalsDTO.get(index)));
	            intervalsDTO.get(index).setPreviousWin(previousWin);
	          } else {
	            IntervaloPremiacaoDTO novoIntervalo = new IntervaloPremiacaoDTO();
	            novoIntervalo.setPreviousWin(Filme.getYear());
	            novoIntervalo.setFollowingWin(Filme.getYear());
	            novoIntervalo.setInterval(0);
	            novoIntervalo.setProducer(produtor);
	            intervalsDTO.add(novoIntervalo);
	          }
	        }
	      }
	      Collections.sort(intervalsDTO);
	      return intervalsDTO.get(intervalsDTO.size() - 1);
	    } catch (Exception e) {
	      return new IntervaloPremiacaoDTO();
	    }
	}

	private List<String> getProducers(Filme filme) {
		// splita os produtores
		List<String> nomes = new ArrayList<>();
		if ( !filme.getProducers().toLowerCase().contains(" and ".toLowerCase()) ) {
			nomes.add(filme.getProducers());
			return nomes;
		}
		String[] produtoresSepAnd = filme.getProducers().split(" and ");
		nomes.add(produtoresSepAnd[1]);
		if ( !produtoresSepAnd[0].toLowerCase().contains(", ".toLowerCase()) ) {
			nomes.add(produtoresSepAnd[0]);
			return nomes;
		}
		String[] produtoresSepVirgula = produtoresSepAnd[0].split(", ");
		for (String nome : produtoresSepVirgula) {
			nomes.add(nome);
		}
		return nomes;
		//return filme.getProducers().replace("and", ",").split(",");
	}

	private int calculaIntervalo(IntervaloPremiacaoDTO intervaloPremiacaoDTO) {
		return intervaloPremiacaoDTO.getFollowingWin() - intervaloPremiacaoDTO.getPreviousWin();
	}

	@Override
	public IntervaloPremiacaoDTO buscarProdutorComPremioMaisRapido() {
		
		List<IntervaloPremiacaoDTO> listaIntervaloDTO = new ArrayList<>();
		
		try {

			List<Filme> filmes = filmeRepository.findAll();

			if (filmes.size() == 0) {
				// retorna vazio
				return new IntervaloPremiacaoDTO();

			}

			for (Filme filme : filmes) {
				
				// verifica se o filme é vencedor
				if (!filme.getWinner()) {
					continue;
				}

				IntervaloPremiacaoDTO intervalo = new IntervaloPremiacaoDTO();
				
				// splita os produtores e percorre
				for (String produtor : this.getProducers(filme)) {

					intervalo.setProducer(produtor);

					int index = listaIntervaloDTO.indexOf(intervalo);
					// verifica se o filme é vencedor
					if (listaIntervaloDTO.contains(intervalo)) {
						if (!listaIntervaloDTO.get(index).getFollowingWin()
								.equals(listaIntervaloDTO.get(index).getPreviousWin())) {
							continue;
						}

						listaIntervaloDTO.get(index).setFollowingWin(filme.getYear());
						listaIntervaloDTO.get(index).setInterval(calculaIntervalo(listaIntervaloDTO.get(index)));

					} else {

						IntervaloPremiacaoDTO novoIntervalo = new IntervaloPremiacaoDTO();
						novoIntervalo.setPreviousWin(filme.getYear());
						novoIntervalo.setFollowingWin(filme.getYear());
						novoIntervalo.setInterval(0);
						novoIntervalo.setProducer(produtor);
						listaIntervaloDTO.add(novoIntervalo);

					}

				}

			}

			Collections.sort(listaIntervaloDTO);

			for (IntervaloPremiacaoDTO intervals : listaIntervaloDTO) {

				if (intervals.getInterval() != 0) {
					return intervals;
				}

			}

			return listaIntervaloDTO.get(0);

		} catch (Exception e) {
			//retorna vazio
			return new IntervaloPremiacaoDTO();

		}
	}

}
