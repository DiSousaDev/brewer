package br.com.diego.brewer.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import br.com.diego.brewer.controller.filter.EstiloFilter;
import br.com.diego.brewer.controller.page.PaginacaoUtil;
import br.com.diego.brewer.model.Estilo;
import br.com.diego.brewer.repository.EstiloRepository;
import br.com.diego.brewer.service.EstiloService;
import br.com.diego.brewer.service.impl.exception.NomeEstiloJaCadastradoException;

@Service
@SuppressWarnings({ "deprecation", "unchecked" })
public class EstiloServiceImpl implements EstiloService {

	@Autowired
	private EstiloRepository repository;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public Estilo salvar(Estilo estilo) {
		Optional<Estilo> estiloOptional = repository.findByNomeIgnoreCase(estilo.getNome());
		if (estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Estilo já cadastrado.");
		}
		return repository.saveAndFlush(estilo);
	}
	
	@Override
	public List<Estilo> buscarTodos() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		
		paginacaoUtil.preparar(criteria, pageable);
				
		filtrarConsulta(estiloFilter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, totalRegistros(estiloFilter));
	}

	
	private Long totalRegistros(EstiloFilter estiloFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		filtrarConsulta(estiloFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void filtrarConsulta(EstiloFilter estiloFilter, Criteria criteria) {

		if (estiloFilter != null) {
			if (!ObjectUtils.isEmpty(estiloFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", estiloFilter.getNome(), MatchMode.ANYWHERE));
			}
			if (estiloFilter.getCodigo() != null) {
				criteria.add(Restrictions.ge("codigo", estiloFilter.getCodigo()));
			}
			
		}
	}

}
