package br.com.diego.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.enums.Origem;
import br.com.diego.brewer.model.enums.Sabor;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.service.EstiloService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private CervejaService cervejaService;
	
	@Autowired
	private EstiloService estiloService;
	
	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/cadastro");
		mv.addObject("estilos", estiloService.buscarTodos()); //Adicionando ao ModelAndView ou adicionando pelo @ModelAttribute funciona igual
		return mv;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(cerveja); 
		}
		attr.addFlashAttribute("mensagem", "Cerveja salva com sucesso!");
		
		cervejaService.salvar(cerveja);
		
		return new ModelAndView("redirect:/cervejas/cadastrar");
	}
	
	
	@ModelAttribute("sabores")
	public Sabor[] getSabores() {
		return Sabor.values();
	}
	
	@ModelAttribute("origens")
	public Origem[] getOrigens() {
		return Origem.values();
	}
	
}
