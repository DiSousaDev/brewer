package br.com.diego.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diego.brewer.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{
	

}
