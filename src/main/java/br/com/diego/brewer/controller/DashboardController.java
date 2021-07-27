package br.com.diego.brewer.controller;

import br.com.diego.brewer.service.ClienteService;
import br.com.diego.brewer.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	@Autowired
	private VendaService vendaService;

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/")
	public ModelAndView abrirPagina() {
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("vendasNoAno", vendaService.valorTotalVendasAno());
		mv.addObject("vendasNoMes", vendaService.valorTotalVendasMes());
		mv.addObject("ticketMedio", vendaService.valorTicketMedioAno());
		mv.addObject("totalClientes", clienteService.quantidadeTotalDeCliente());

		return mv;
	}

	}
