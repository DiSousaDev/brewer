package br.com.diego.brewer.service.impl;

import br.com.diego.brewer.controller.filter.CervejaFilter;
import br.com.diego.brewer.controller.page.PaginacaoUtil;
import br.com.diego.brewer.dto.CervejaDTO;
import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.repository.CervejaRepository;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.service.event.CervejaSalvaEvent;
import br.com.diego.brewer.service.impl.exception.ImpossivelExcluirEntidadeException;
import br.com.diego.brewer.service.impl.exception.SkuCervejaJaCadastradoException;
import br.com.diego.brewer.storage.FotoStorage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings({ "deprecation", "unchecked" })
public class CervejaServiceImpl implements CervejaService {

	@Autowired
	private CervejaRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;

	@Autowired
	private FotoStorage fotoStorage;

	@Override
	@Transactional(readOnly = false)
	public Cerveja salvar(Cerveja cerveja) {
		Optional<Cerveja> cervejaOptional = repository.findBySku(cerveja.getSku());
		if (cervejaOptional.isPresent() && !cervejaOptional.get().equals(cerveja)) {
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
	public List<CervejaDTO> buscarPorSkuOuNome(String skuOuNome){
		String jpql = "select new br.com.diego.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) "
				+ "from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";
		List<CervejaDTO> cervejasFiltradas = manager.createQuery(jpql, CervejaDTO.class)
				.setParameter("skuOuNome", skuOuNome + "%")
				.getResultList();
		return cervejasFiltradas;
	}

	@Override
	public Cerveja buscarPorCodigo(Long codigoCerveja){
		return repository.findByCodigo(codigoCerveja);
	}

	@Override
	@Transactional
	public void excluir(Cerveja cerveja){

		try {
			String foto = cerveja.getFoto();
			repository.delete(cerveja);
			repository.flush();
			fotoStorage.excluir(foto);
		} catch (Exception e){
			throw new ImpossivelExcluirEntidadeException("Erro ao excluir, já existe registro associado.");
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);

		paginacaoUtil.preparar(criteria, pageable);
		
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