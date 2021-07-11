package br.com.diego.brewer.service.impl;

import br.com.diego.brewer.controller.filter.UsuarioFilter;
import br.com.diego.brewer.controller.page.PaginacaoUtil;
import br.com.diego.brewer.model.Grupo;
import br.com.diego.brewer.model.Usuario;
import br.com.diego.brewer.model.UsuarioGrupo;
import br.com.diego.brewer.model.enums.StatusUsuario;
import br.com.diego.brewer.repository.UsuarioRepository;
import br.com.diego.brewer.service.UsuarioService;
import br.com.diego.brewer.service.impl.exception.EmailJaCadastradoException;
import br.com.diego.brewer.service.impl.exception.SenhaObrigatoriaUsuarioException;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PersistenceContext
	private EntityManager manager;
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	@Override
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioOptional = repository.findByEmail(usuario.getEmail());
		
		if(usuarioOptional.isPresent()) {
			throw new EmailJaCadastradoException("E-mail já cadastrado.");
		}
		
		if(usuario.isNovo() && ObjectUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário.");
		}
		
		if(usuario.isNovo()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		return repository.saveAndFlush(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);

		paginacaoUtil.preparar(criteria, pageable);

		filtrarConsulta(filtro, criteria);

		List<Usuario> filtrados = criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));

		return new PageImpl<>(filtrados, pageable, totalRegistros(filtro));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos(){
		return repository.findAll();
	}

	@Override
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario){
		statusUsuario.executar(codigos, repository);
	}

	private Long totalRegistros(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		filtrarConsulta(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void filtrarConsulta(UsuarioFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!ObjectUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			if (!ObjectUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getNome(), MatchMode.START));
			}

			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));

					subqueries.add(Subqueries.propertyIn("codigo", dc));
				}

				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}

}
