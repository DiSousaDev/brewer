package br.com.diego.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.model.Estilo;
import br.com.diego.brewer.service.EstiloService;
import br.com.diego.brewer.service.impl.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilos")
public class EstiloController {

	@Autowired
	private EstiloService estiloService;
	
	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina(Estilo estilo) {
		ModelAndView mv = new ModelAndView("estilo/cadastro");
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {

		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}

		estilo = estiloService.salvar(estilo);
		return ResponseEntity.ok(estilo);
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina(estilo); 
		}

		try {
			estiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return abrirPagina(estilo); 
		}

		attr.addFlashAttribute("mensagem", "Estilo adicionado com sucesso!");
		return new ModelAndView("redirect:/estilos/cadastrar");
	}
	
}
