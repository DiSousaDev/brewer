package br.com.diego.brewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diego.brewer.model.Estilo;
import br.com.diego.brewer.repository.EstiloRepository;
import br.com.diego.brewer.service.EstiloService;

@Service
public class EstiloServiceImpl implements EstiloService {

	@Autowired
	private EstiloRepository repository;

	@Override
	public List<Estilo> buscarTodos() {
		return repository.findAll();
	}

}
