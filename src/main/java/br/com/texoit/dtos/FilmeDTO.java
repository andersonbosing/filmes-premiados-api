package br.com.texoit.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.texoit.models.Filme;
import io.swagger.annotations.ApiModel;

@ApiModel
public class FilmeDTO {
	
	private Long id;
	private Integer year;
	private String title;
	private String studios;
	private String producers; 
	private Boolean winner;
	
	public FilmeDTO() {
	}

	public FilmeDTO(Filme filme) {
		super();
		this.id = filme.getId();
		this.year = filme.getYear();
		this.title = filme.getTitle();
		this.studios = filme.getStudios();
		this.producers = filme.getProducers();
		this.winner = filme.getWinner();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		this.studios = studios;
	}

	public String getProducers() {
		return producers;
	}

	public void setProducers(String producers) {
		this.producers = producers;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public static List<FilmeDTO> converterLista(List<Filme> listaFilmes) {
		return listaFilmes.stream().map(FilmeDTO::new).collect(Collectors.toList());
	}

	public static Filme converterToObject(FilmeDTO filmeDTO) {
		
		Filme filme = new Filme();
		filme.setId(filmeDTO.getId());
		filme.setProducers(filmeDTO.getProducers());
		filme.setStudios(filmeDTO.getStudios());
		filme.setTitle(filmeDTO.getTitle());
		filme.setWinner(filmeDTO.getWinner());
		filme.setYear(filmeDTO.getYear());
		
		return filme;
		
	}
	
	public static FilmeDTO converterToDTO(Filme filme) {
		
		FilmeDTO filmeDTO = new FilmeDTO(filme);
		
		return filmeDTO;
		
	}
	
}
