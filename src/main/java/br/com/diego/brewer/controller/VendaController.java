package br.com.diego.brewer.controller;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.Usuario;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.session.TabelaItensVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private CervejaService cervejaService;

	@Autowired
	private TabelaItensVenda tabelaItensVenda;

	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina() {
		ModelAndView mv = new ModelAndView("venda/cadastro");
		return mv;
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		Cerveja cerveja = cervejaService.buscarPorCodigo(codigoCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		mv.addObject("itens", tabelaItensVenda.getItens());
		return mv;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, ModelMap model, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return abrirPagina();
		}
//
//		try {
//			usuarioService.salvar(usuario);
//			attr.addFlashAttribute("mensagem", "Usuário adicionado com sucesso!");
//		} catch (EmailJaCadastradoException e) {
//			result.rejectValue("email", e.getMessage(), e.getMessage());
//			return abrirPagina(usuario);
//		} catch (SenhaObrigatoriaUsuarioException e) {
//			result.rejectValue("senha", e.getMessage(), e.getMessage());
//			return abrirPagina(usuario);
//		}
//
//		attr.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
		return new ModelAndView("redirect:/usuarios/cadastrar");
	}

}
