package br.com.texoit.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.texoit.models.Filme;
import br.com.texoit.respositories.FilmeRepository;

@Service
public class CarregarCSVService {

	@Autowired
	private FilmeRepository filmeRepository;
	
	@PostConstruct
	public void loadFile() throws FileNotFoundException {

		List<Filme> listaFilmesArquivo = new ArrayList<>();
		
		List<List<String>> records = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File("movielist.csv"))) {
			
		    while (scanner.hasNextLine()) {
		        records.add(getRegistro(scanner.nextLine()));
		    }
		    
		}
		
		removerCabecalhoCSV(records);
		
		for (int i = 0; i < records.size(); i++) {
			
			Filme filme = new Filme();
			filme.setYear(Integer.valueOf(records.get(i).get(0)));
			filme.setTitle(records.get(i).get(1));
			filme.setStudios(records.get(i).get(2));
			filme.setProducers(records.get(i).get(3));
			filme.setWinner(false);
			if (records.get(i).size() > 4) {
				
				if (!records.get(i).get(4).isEmpty()) 
					filme.setWinner(Boolean.valueOf(
								(records.get(i).get(4).equalsIgnoreCase("yes")) ? true : false
							));
				
			}
			
			listaFilmesArquivo.add(filme);
		
		}
		
		filmeRepository.saveAll(listaFilmesArquivo);
		

	}
	
	private List<String> getRegistro(String line) {
		
	    List<String> values = new ArrayList<String>();
	    
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(";");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    
	    return values;
	    
	}
	
	private void removerCabecalhoCSV(List<List<String>> records) {
		
		if(!records.isEmpty()) {
			records.remove(0);
		}
		
	}

}
