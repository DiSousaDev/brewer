package br.com.diego.brewer.controller;

import br.com.diego.brewer.configuration.security.UsuarioSistema;
import br.com.diego.brewer.controller.filter.VendaFilter;
import br.com.diego.brewer.controller.page.PageWrapper;
import br.com.diego.brewer.controller.validator.VendaValidator;
import br.com.diego.brewer.mail.Mailer;
import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.ItemVenda;
import br.com.diego.brewer.model.Venda;
import br.com.diego.brewer.model.enums.StatusVenda;
import br.com.diego.brewer.model.enums.TipoPessoa;
import br.com.diego.brewer.service.CervejaService;
import br.com.diego.brewer.service.VendaService;
import br.com.diego.brewer.session.TabelaItensSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

	@Autowired
	private Mailer mailer;

	@InitBinder("venda")
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}

	@RequestMapping("/cadastrar")
	public ModelAndView abrirPagina(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/cadastro");

		setUuid(venda);

		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));

		return mv;
	}

	@PostMapping(value="/cadastrar", params = "salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return abrirPagina(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());

		vendaService.salvar(venda);

		attr.addFlashAttribute("mensagem", "Venda salva com sucesso!");
		return new ModelAndView("redirect:/vendas/cadastrar");
	}

	@PostMapping(value="/cadastrar", params = "emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return abrirPagina(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());

		vendaService.emitir(venda);
		attr.addFlashAttribute("mensagem", "Venda emitida com sucesso!");
		return new ModelAndView("redirect:/vendas/cadastrar");
	}

	@PostMapping(value="/cadastrar", params = "enviarEmail")
	public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return abrirPagina(venda);
		}

		venda.setUsuario(usuarioSistema.getUsuario());

		venda = vendaService.salvar(venda);

		mailer.enviar(venda);

		attr.addFlashAttribute("mensagem", String.format("Venda nº %d salva com sucesso e e-mail enviado!", venda.getCodigo()));
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

	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter,
								  @PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/venda/pesquisa");
		mv.addObject("todosStatus", StatusVenda.values());
		mv.addObject("tiposPessoa", TipoPessoa.values());

		PageWrapper<Venda> paginaWrapper = new PageWrapper<>(vendaService.filtrar(vendaFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Venda venda = vendaService.buscarComItens(codigo);

		setUuid(venda);
		for(ItemVenda item : venda.getItens()) {
			tabelaItens.adicionarItem(venda.getUuid(), item.getCerveja(), item.getQuantidade());
		}

		ModelAndView mv = abrirPagina(venda);
		mv.addObject(venda);
		return mv;
	}

	@PostMapping(value="/cadastrar", params = "cancelar")
	public ModelAndView cancelar(Venda venda, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		try {
			vendaService.cancelar(venda);
		} catch (AccessDeniedException e) {
			return new ModelAndView("/403");
		}

		attr.addFlashAttribute("mensagenm", "Venda cancelada com sucesso!");
		return new ModelAndView("redirect:/vendas/" + venda.getCodigo());
	}

	private ModelAndView mvTabelaItensVenda(String uuid){
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}

	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();

		vendaValidator.validate(venda, result);
	}

	private void setUuid(Venda venda) {
		if(ObjectUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
	}

}
