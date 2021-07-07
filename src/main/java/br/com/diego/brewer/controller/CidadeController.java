package br.com.diego.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.controller.filter.CidadeFilter;
import br.com.diego.brewer.controller.page.PageWrapper;
import br.com.diego.brewer.model.Cidade;
import br.com.diego.brewer.service.CidadeService;
import br.com.diego.brewer.service.EstadoService;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private EstadoService estadoService;

	@RequestMapping("/cadastrar")
	@Cacheable("estados")
	public ModelAndView abrirPagina(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/cadastro");
		mv.addObject("estados", estadoService.buscarTodos());
		return mv;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(cidade);
		}
		try {
			cidadeService.salvar(cidade);
			attr.addFlashAttribute("mensagem", "Cidade adicionada com sucesso!");
		} catch (Exception e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return abrirPagina(cidade);
		}

		attr.addFlashAttribute("mensagem", "Cidade adicionada com sucesso!");
		return new ModelAndView("redirect:/cidades/cadastrar");
	}

	@Cacheable(value = "cidades", key = "#codigoEstado")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> buscarPorCodigoEstado(@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		return cidadeService.buscarPorCodigoEstado(codigoEstado);
	}

	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult binding, @PageableDefault(size = 6) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/cidade/pesquisa");

		PageWrapper<Cidade> paginaWrapper = new PageWrapper<>(cidadeService.filtrar(cidadeFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);

		return mv;
	}

}
