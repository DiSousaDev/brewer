package br.com.diego.brewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diego.brewer.model.Grupo;
import br.com.diego.brewer.repository.GrupoRepository;
import br.com.diego.brewer.service.GrupoService;

@Service
public class GrupoServiceImpl implements GrupoService {

	@Autowired
	private GrupoRepository repository;
		
	@Override
	public List<Grupo> buscarTodos() {
		return repository.findAll();
	}

}
