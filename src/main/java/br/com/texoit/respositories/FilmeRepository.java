package br.com.texoit.respositories;

import org.springframework.stereotype.Repository;

import br.com.texoit.models.Filme;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{

}
