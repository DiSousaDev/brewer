package br.com.diego.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.model.Estado;
import br.com.diego.brewer.model.enums.TipoPessoa;
import br.com.diego.brewer.service.EstadoService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private EstadoService estadoService;
	
	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina() {
		ModelAndView mv = new ModelAndView("cliente/cadastro");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		return mv;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(); 
		}
		attr.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
		return new ModelAndView("redirect:/clientes/cadastrar");
	}
	
	@ModelAttribute("estados")
	public List<Estado> getEstados() {
		return estadoService.buscarTodos();
	}
	
}
