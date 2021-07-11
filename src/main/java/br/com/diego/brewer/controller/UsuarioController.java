package br.com.diego.brewer.controller;

import br.com.diego.brewer.controller.filter.CervejaFilter;
import br.com.diego.brewer.controller.filter.UsuarioFilter;
import br.com.diego.brewer.controller.page.PageWrapper;
import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.Usuario;
import br.com.diego.brewer.model.enums.Origem;
import br.com.diego.brewer.model.enums.StatusUsuario;
import br.com.diego.brewer.service.GrupoService;
import br.com.diego.brewer.service.UsuarioService;
import br.com.diego.brewer.service.impl.exception.EmailJaCadastradoException;
import br.com.diego.brewer.service.impl.exception.SenhaObrigatoriaUsuarioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoService grupoService;
	
	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/cadastro");
		mv.addObject("grupos", grupoService.buscarTodos());
		return mv;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(usuario);
		}
		
		try {
			usuarioService.salvar(usuario);
			attr.addFlashAttribute("mensagem", "Usuário adicionado com sucesso!");
		} catch (EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return abrirPagina(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return abrirPagina(usuario);
		}
		
		attr.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
		return new ModelAndView("redirect:/usuarios/cadastrar");
	}

	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult binding, @PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/usuario/pesquisa");
		mv.addObject("grupos", grupoService.buscarTodos());

		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarioService.filtrar(usuarioFilter, pageable), httpServletRequest);

		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		usuarioService.alterarStatus(codigos, statusUsuario);
	}

}
