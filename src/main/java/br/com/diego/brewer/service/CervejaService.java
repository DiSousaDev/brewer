package br.com.diego.brewer.service;

import java.util.List;

import br.com.diego.brewer.model.Cerveja;

public interface CervejaService {

	void salvar(Cerveja cerveja);
	
	List<Cerveja> buscarTodas();
	
}
