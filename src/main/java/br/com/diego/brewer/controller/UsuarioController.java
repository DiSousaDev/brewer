package br.com.diego.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	
	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina() {
		ModelAndView mv = new ModelAndView("usuario/cadastro");
		return mv;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(); 
		}
		attr.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
		return new ModelAndView("redirect:/usuarios/cadastrar");
	}
	
}
