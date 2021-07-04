package br.com.diego.brewer.service.impl;

import java.util.List;
import java.util.Optional;

import br.com.diego.brewer.controller.filter.CidadeFilter;
import br.com.diego.brewer.controller.filter.ClienteFilter;
import br.com.diego.brewer.controller.page.PaginacaoUtil;
import br.com.diego.brewer.model.Cliente;
import br.com.diego.brewer.service.impl.exception.CidadeJaCadastradaException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.diego.brewer.model.Cidade;
import br.com.diego.brewer.repository.CidadeRepository;
import br.com.diego.brewer.service.CidadeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CidadeServiceImpl implements CidadeService {

	@Autowired
	private CidadeRepository repository;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public List<Cidade> buscarTodos(){
		return repository.findAll();
	}

	@Override
	public void salvar(Cidade cidade){
		Optional<Cidade> cidadeOptional = repository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());

		if (cidadeOptional.isPresent()) {
			throw new CidadeJaCadastradaException("Cidade já cadastrada.");
		}
		repository.save(cidade);
	}

	@Override
	public List<Cidade> buscarPorCodigoEstado(Long codigoEstado){
		return repository.findByEstadoCodigo(codigoEstado);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable){
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);

		paginacaoUtil.preparar(criteria, pageable);
		filtrarConsulta(cidadeFilter, criteria);
		criteria.createAlias("estado", "e");

		return new PageImpl<>(criteria.list(), pageable, totalRegistros(cidadeFilter));
	}

	private Long totalRegistros(CidadeFilter cidadeFilter){
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		filtrarConsulta(cidadeFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		criteria.createAlias("estado", "e");

		return (Long) criteria.uniqueResult();
	}

	private void filtrarConsulta(CidadeFilter cidadeFilter, Criteria criteria){

		if (cidadeFilter != null) {
			if (cidadeFilter.getEstado() != null) {
				criteria.add(Restrictions.eq("estado", cidadeFilter.getEstado()));
			}

			if (!ObjectUtils.isEmpty(cidadeFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", cidadeFilter.getNome(), MatchMode.ANYWHERE));
			}
		}
	}
}