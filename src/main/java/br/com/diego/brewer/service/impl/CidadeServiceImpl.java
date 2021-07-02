package br.com.diego.brewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diego.brewer.model.Cidade;
import br.com.diego.brewer.repository.CidadeRepository;
import br.com.diego.brewer.service.CidadeService;

@Service
public class CidadeServiceImpl implements CidadeService {

	@Autowired
	private CidadeRepository repository;
		
	@Override
	public List<Cidade> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public List<Cidade> buscarPorCodigoEstado(Long codigoEstado) {
		return repository.findByEstadoCodigo(codigoEstado);
	}

	
}
