package br.com.diego.brewer.service;

import java.util.List;

import br.com.diego.brewer.model.Cidade;

public interface CidadeService {

	List<Cidade> buscarTodos();
	
	List<Cidade> buscarPorCodigoEstado(Long codigoEstado);

}
