package br.com.diego.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diego.brewer.model.Estilo;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long>{

}
