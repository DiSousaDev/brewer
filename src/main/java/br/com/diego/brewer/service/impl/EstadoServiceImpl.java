package br.com.diego.brewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diego.brewer.model.Estado;
import br.com.diego.brewer.repository.EstadoRepository;
import br.com.diego.brewer.service.EstadoService;

@Service
public class EstadoServiceImpl implements EstadoService {

	@Autowired
	private EstadoRepository repository;
		
	@Override
	public List<Estado> buscarTodos() {
		return repository.findAll();
	}

}
