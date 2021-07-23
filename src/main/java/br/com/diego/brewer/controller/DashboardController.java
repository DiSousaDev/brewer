package br.com.diego.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	@GetMapping("/")
	public ModelAndView abrirPagina() {
		ModelAndView mv = new ModelAndView("dashboard");
		return mv;
	}

}
