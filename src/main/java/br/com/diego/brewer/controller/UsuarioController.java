package br.com.diego.brewer.controller;

import br.com.diego.brewer.model.Usuario;
import br.com.diego.brewer.service.GrupoService;
import br.com.diego.brewer.service.UsuarioService;
import br.com.diego.brewer.service.impl.exception.EmailJaCadastradoException;
import br.com.diego.brewer.service.impl.exception.SenhaObrigatoriaUsuarioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
	
}
