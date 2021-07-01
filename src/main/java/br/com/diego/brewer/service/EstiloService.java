package br.com.diego.brewer.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.diego.brewer.controller.filter.EstiloFilter;
import br.com.diego.brewer.model.Estilo;

public interface EstiloService {

	Estilo salvar(Estilo estilo);
	
	List<Estilo> buscarTodos();

	Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable);
	
}
