package br.com.diego.brewer.repository;

import br.com.diego.brewer.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    Optional<Cliente> findByCpfOuCnpj(String cpjOuCnpj);

    List<Cliente> findByNomeStartingWithIgnoreCase(String nome);

}
