package br.com.diego.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.brewer.model.Cidade;
import br.com.diego.brewer.service.CidadeService;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
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
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> buscarPorCodigoEstado(@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		return cidadeService.buscarPorCodigoEstado(codigoEstado);
	}
	
}
