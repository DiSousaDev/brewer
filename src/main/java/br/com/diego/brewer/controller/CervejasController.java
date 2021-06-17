package br.com.diego.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.model.Cerveja;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {

	
	@RequestMapping("/cadastrar")
	public String abrirPagina(Cerveja cerveja) {
		return "cerveja/cadastro";
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(cerveja); 
		}
		attr.addFlashAttribute("mensagem", "Cerveja salva com sucesso!");
		return "redirect:/cervejas/cadastrar";
	}
	
	@RequestMapping("/teste")
	public String teste(Cerveja cerveja) {
		return "cerveja/cadastro-produto";
	}
}
