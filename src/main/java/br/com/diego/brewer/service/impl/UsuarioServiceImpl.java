package br.com.diego.brewer.service.impl;

import br.com.diego.brewer.model.Usuario;
import br.com.diego.brewer.repository.UsuarioRepository;
import br.com.diego.brewer.service.UsuarioService;
import br.com.diego.brewer.service.impl.exception.EmailJaCadastradoException;
import br.com.diego.brewer.service.impl.exception.SenhaObrigatoriaUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private EntityManager entitymanager;

	private PasswordEncoder passwordEncoder;
	
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


}
