package br.com.diego.brewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.repository.CervejaRepository;
import br.com.diego.brewer.service.CervejaService;

@Service
public class CervejaServiceImpl implements CervejaService {

	@Autowired
	private CervejaRepository repository;

	@Override
	@Transactional(readOnly = false)
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);		
	}

	@Override
	public List<Cerveja> buscarTodas() {
		return repository.findAll();
	}

}
