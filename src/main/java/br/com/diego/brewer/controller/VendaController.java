package br.com.diego.brewer.controller;

import br.com.diego.brewer.configuration.security.UsuarioSistema;
import br.com.diego.brewer.controller.validator.VendaValidator;
import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.Venda;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.service.VendaService;
import br.com.diego.brewer.session.TabelaItensSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private CervejaService cervejaService;

	@Autowired
	private VendaService vendaService;

	@Autowired
	private TabelaItensSession tabelaItens;

	@Autowired
	private VendaValidator vendaValidator;

	@InitBinder
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}

	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/cadastro");
		if(ObjectUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));

		return mv;
	}

	@PostMapping("/cadastrar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();

		vendaValidator.validate(venda, result);
		if(result.hasErrors()) {
			return abrirPagina(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());

		vendaService.salvar(venda);
		attr.addFlashAttribute("mensagem", "Venda salva com sucesso!");
		return new ModelAndView("redirect:/vendas/cadastrar");
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		Cerveja cerveja = cervejaService.buscarPorCodigo(codigoCerveja);
		tabelaItens.adicionarItem(uuid, cerveja, 1);
		return mvTabelaItensVenda(uuid);
	}

	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoCerveja, Integer quantidade, String uuid) {
		Cerveja cerveja = cervejaService.buscarPorCodigo(codigoCerveja);
		tabelaItens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		return mvTabelaItensVenda(uuid);
	}

	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable String uuid) {
		tabelaItens.excluirItem(uuid, cerveja);
		return mvTabelaItensVenda(uuid);
	}

	private ModelAndView mvTabelaItensVenda(String uuid){
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}

}
