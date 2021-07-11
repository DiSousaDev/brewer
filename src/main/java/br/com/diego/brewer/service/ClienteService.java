package br.com.diego.brewer.service;

import br.com.diego.brewer.controller.filter.ClienteFilter;
import br.com.diego.brewer.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

	Cliente salvar(Cliente cliente);

	Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);

    List<Cliente> buscarPorNome(String nome);
}
