package br.com.diego.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.controller.filter.CervejaFilter;
import br.com.diego.brewer.controller.page.PageWrapper;
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
		mv.addObject("estilos", estiloService.buscarTodos()); // Adicionando ao ModelAndView ou adicionando pelo
																// @ModelAttribute funciona igual
		return mv;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return abrirPagina(cerveja);
		}

		try {
			cervejaService.salvar(cerveja);
			attr.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
		} catch (Exception e) {
			result.rejectValue("sku", e.getMessage(), e.getMessage());
			return abrirPagina(cerveja);
		}
		attr.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
		return new ModelAndView("redirect:/cervejas/cadastrar");
	}

	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult binding, @PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/cerveja/pesquisa");
		mv.addObject("estilos", estiloService.buscarTodos()); // Adicionando ao ModelAndView ou adicionando pelo @ModelAttribute funciona igual
		
		PageWrapper<Cerveja> paginaWrapper = new PageWrapper<>(cervejaService.filtrar(cervejaFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
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
