package br.com.diego.brewer.controller;

import java.util.List;

import br.com.diego.brewer.controller.filter.ClienteFilter;
import br.com.diego.brewer.controller.filter.EstiloFilter;
import br.com.diego.brewer.controller.page.PageWrapper;
import br.com.diego.brewer.model.Estilo;
import br.com.diego.brewer.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.model.Cliente;
import br.com.diego.brewer.model.Estado;
import br.com.diego.brewer.model.enums.TipoPessoa;
import br.com.diego.brewer.service.EstadoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EstadoService estadoService;
	
	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/cadastro");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoService.buscarTodos());
		return mv;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return abrirPagina(cliente); 
		}
		try {
			clienteService.salvar(cliente);
			attr.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
		} catch (Exception e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return abrirPagina(cliente);
		}
		return new ModelAndView("redirect:/clientes/cadastrar");
	}

	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult binding, @PageableDefault(size = 6) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/cliente/pesquisa");

		PageWrapper<Cliente> paginaWrapper = new PageWrapper<>(clienteService.filtrar(clienteFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);

		return mv;
	}

}
