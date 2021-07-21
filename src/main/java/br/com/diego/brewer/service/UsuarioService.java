package br.com.diego.brewer.service;

import br.com.diego.brewer.controller.filter.UsuarioFilter;
import br.com.diego.brewer.model.Usuario;
import br.com.diego.brewer.model.enums.StatusUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

	Usuario salvar(Usuario usuario);

    Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);

    List<Usuario> buscarTodos();

    void alterarStatus(Long[] codigos, StatusUsuario statusUsuario);

    Usuario buscarComGrupos(Long codigo);
}
