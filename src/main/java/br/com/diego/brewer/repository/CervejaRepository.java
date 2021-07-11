package br.com.diego.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diego.brewer.model.Cerveja;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long> {
	
	Optional<Cerveja> findBySku(String sku);

	Cerveja findByCodigo(Long codigo);

}
