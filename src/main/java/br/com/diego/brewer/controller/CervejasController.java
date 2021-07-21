package br.com.diego.brewer.controller;

import br.com.diego.brewer.controller.filter.CervejaFilter;
import br.com.diego.brewer.controller.page.PageWrapper;
import br.com.diego.brewer.dto.CervejaDTO;
import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.enums.Origem;
import br.com.diego.brewer.model.enums.Sabor;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.service.EstiloService;
import br.com.diego.brewer.service.impl.exception.ImpossivelExcluirEntidadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
		mv.addObject("sabores", Sabor.values());			// @ModelAttribute funciona igual
		mv.addObject("origens", Origem.values());
		return mv;
	}

	@RequestMapping(value = { "/cadastrar", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return abrirPagina(cerveja);
		}

		try {
			exibirMensagem(cerveja, attr);
			cervejaService.salvar(cerveja);
		} catch (Exception e) {
			result.rejectValue("sku", e.getMessage(), e.getMessage());
			return abrirPagina(cerveja);
		}
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

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome) {
		return cervejaService.buscarPorSkuOuNome(skuOuNome);
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cerveja cerveja) {
		try {
			cervejaService.excluir(cerveja);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cerveja cerveja) {
		ModelAndView mv = abrirPagina(cerveja);
		mv.addObject(cerveja);
		return mv;
	}

	private void exibirMensagem(Cerveja cerveja, RedirectAttributes attr) {
		if(cerveja.isNova()) {
			attr.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
		} else {
			attr.addFlashAttribute("mensagem", "Cerveja alterada com sucesso!");
		}
	}

}
