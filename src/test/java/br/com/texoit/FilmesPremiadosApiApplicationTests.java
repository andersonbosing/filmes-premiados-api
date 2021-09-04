package br.com.texoit;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.texoit.dtos.FilmeDTO;
import br.com.texoit.services.FilmeServiceImpl;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class FilmesPremiadosApiApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilmeServiceImpl service;
	
	
	@Test
	void testeEndpointListarTodos() throws Exception {
		
		 mockMvc.perform(MockMvcRequestBuilders.
			        get("/filme")).
			        andExpect(MockMvcResultMatchers.status().isOk());  
		 
	}
	
	@Test
	void testeEndpointPost() throws Exception {
		
		FilmeDTO filmeDTO = new FilmeDTO();
		filmeDTO.setProducers("TESTE");
		filmeDTO.setStudios("TESTE");
		filmeDTO.setTitle("TESTE");
		filmeDTO.setWinner(false);
		filmeDTO.setYear(1999);
		
		
		mockMvc.perform(post("/filme")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(filmeDTO)))
	            .andExpect(status().isCreated()); 
		 
	}
	
	@Test
	void testeEndpointAwards() throws Exception {
		
		mockMvc.perform(
				get("/filme/awards"))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.min.producer", is("Joel Silver")))
			    .andExpect(jsonPath("$.min.interval", is(1)))
			    .andExpect(jsonPath("$.min.previousWin", is(1990)))
			    .andExpect(jsonPath("$.min.followingWin", is(1991)))
			    .andExpect(jsonPath("$.max.producer", is("Matthew Vaughn")))
			    .andExpect(jsonPath("$.max.interval", is(13)))
			    .andExpect(jsonPath("$.max.previousWin", is(2002)))
			    .andExpect(jsonPath("$.max.followingWin", is(2015)));		
		
		
	}
	

}
