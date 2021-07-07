package br.com.diego.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diego.brewer.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	

}
