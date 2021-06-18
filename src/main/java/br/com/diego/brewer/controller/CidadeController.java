package br.com.diego.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	
	@RequestMapping("/cadastrar")
	public String abrirPagina() {
		return "cidade/cadastro";
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(); 
		}
		attr.addFlashAttribute("mensagem", "Cidade adicionada com sucesso!");
		return "redirect:/cidades/cadastrar";
	}
	
}
