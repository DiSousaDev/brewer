package br.com.diego.brewer.service.impl;

import br.com.diego.brewer.controller.filter.ClienteFilter;
import br.com.diego.brewer.controller.page.PaginacaoUtil;
import br.com.diego.brewer.model.Cliente;
import br.com.diego.brewer.repository.ClienteRepository;
import br.com.diego.brewer.service.ClienteService;
import br.com.diego.brewer.service.impl.exception.CpfOuCnpjJaCadastradoException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repository;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public Cliente salvar(Cliente cliente) {
		Optional<Cliente> clienteOptional = repository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		if(clienteOptional.isPresent()) {
			throw new CpfOuCnpjJaCadastradoException("CPF/CNPJ já cadastrado.");
		}
		return repository.saveAndFlush(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable){
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);

		paginacaoUtil.preparar(criteria, pageable);
		filtrarConsulta(clienteFilter, criteria);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);

		return new PageImpl<>(criteria.list(), pageable, totalRegistros(clienteFilter));
	}

	private Long totalRegistros(ClienteFilter clienteFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		filtrarConsulta(clienteFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void filtrarConsulta(ClienteFilter clienteFilter, Criteria criteria) {

		if (clienteFilter != null) {
			if (!ObjectUtils.isEmpty(clienteFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", clienteFilter.getNome(), MatchMode.ANYWHERE));
			}
			if (clienteFilter.getCpfOuCnpj() != null) {
				criteria.add(Restrictions.ge("cpfOuCnpj", clienteFilter.getCpfOuCnpj()));
			}

		}
	}

}
