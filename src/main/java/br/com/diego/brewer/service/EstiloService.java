package br.com.diego.brewer.service;

import java.util.List;

import br.com.diego.brewer.model.Estilo;

public interface EstiloService {

	Estilo salvar(Estilo estilo);
	
	List<Estilo> buscarTodos();
	
}
