package br.com.diego.brewer.repository;

import java.util.List;
import java.util.Optional;

import br.com.diego.brewer.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diego.brewer.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

	List<Cidade> findByEstadoCodigo(Long codigoEstado);

	Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);
}
