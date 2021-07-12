package br.com.diego.brewer.controller;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.Usuario;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.session.TabelaItensVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		Cerveja cerveja = cervejaService.buscarPorCodigo(codigoCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		return mvTabelaItensVenda();
	}

	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoCerveja, Integer quantidade) {
		Cerveja cerveja = cervejaService.buscarPorCodigo(codigoCerveja);
		tabelaItensVenda.alterarQuantidadeItens(cerveja, quantidade);
		return mvTabelaItensVenda();
	}

	@DeleteMapping("/item/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja) {
		tabelaItensVenda.excluirItem(cerveja);
		return mvTabelaItensVenda();
	}

	private ModelAndView mvTabelaItensVenda(){
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		return mv;
	}

}
