package br.com.diego.brewer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diego.brewer.model.Estilo;
import br.com.diego.brewer.repository.EstiloRepository;
import br.com.diego.brewer.service.EstiloService;
import br.com.diego.brewer.service.impl.exception.NomeEstiloJaCadastradoException;

@Service
public class EstiloServiceImpl implements EstiloService {

	@Autowired
	private EstiloRepository repository;

	@Override
	public void salvar(Estilo estilo) {
		Optional<Estilo> estiloOptional = repository.findByNomeIgnoreCase(estilo.getNome());
		if (estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Estilo já cadastrado.");
		}
		repository.save(estilo);
	}
	
	@Override
	public List<Estilo> buscarTodos() {
		return repository.findAll();
	}

}
