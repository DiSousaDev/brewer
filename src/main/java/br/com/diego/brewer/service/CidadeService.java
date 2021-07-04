package br.com.diego.brewer.service;

import br.com.diego.brewer.controller.filter.CidadeFilter;
import br.com.diego.brewer.model.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CidadeService {

	List<Cidade> buscarTodos();

	void salvar(Cidade cidade);
	
	List<Cidade> buscarPorCodigoEstado(Long codigoEstado);

	Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable);

}
