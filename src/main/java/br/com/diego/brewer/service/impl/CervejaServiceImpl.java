package br.com.diego.brewer.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import br.com.diego.brewer.controller.filter.CervejaFilter;
import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.repository.CervejaRepository;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.service.event.CervejaSalvaEvent;
import br.com.diego.brewer.service.impl.exception.SkuCervejaJaCadastradoException;

@Service
@SuppressWarnings({ "deprecation", "unchecked" })
public class CervejaServiceImpl implements CervejaService {

	@Autowired
	private CervejaRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PersistenceContext
	private EntityManager manager;

	@Override
	@Transactional(readOnly = false)
	public Cerveja salvar(Cerveja cerveja) {
		Optional<Cerveja> cervejaOptional = repository.findBySku(cerveja.getSku());
		if (cervejaOptional.isPresent()) {
			throw new SkuCervejaJaCadastradoException("Sku já cadastrado.");
		}
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
		return repository.saveAndFlush(cerveja);
	}

	@Override
	public List<Cerveja> buscarTodas() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);

		Sort sort = pageable.getSort();
		
		if (sort.isSorted()) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
		
		filtrarConsulta(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, totalRegistros(filtro));
	}

	private Long totalRegistros(CervejaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		filtrarConsulta(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void filtrarConsulta(CervejaFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!ObjectUtils.isEmpty(filtro.getSku())) {
				criteria.add(Restrictions.eq("sku", filtro.getSku()));
			}

			if (!ObjectUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if (isEstiloPresente(filtro)) {
				criteria.add(Restrictions.eq("estilo", filtro.getEstilo()));
			}

			if (filtro.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filtro.getSabor()));
			}

			if (filtro.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filtro.getOrigem()));
			}

			if (filtro.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filtro.getValorDe()));
			}

			if (filtro.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filtro.getValorAte()));
			}
		}
	}

	private boolean isEstiloPresente(CervejaFilter filtro) {
		return filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null;
	}

}