package br.com.diego.brewer.service.impl;

import br.com.diego.brewer.model.ItemVenda;
import br.com.diego.brewer.model.Venda;
import br.com.diego.brewer.repository.VendaRepository;
import br.com.diego.brewer.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class VendaServiceImpl implements VendaService {

	@Autowired
	private VendaRepository repository;

	@Override
	@Transactional
	public void salvar(Venda venda) {
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}

		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.now()));
		}

		repository.save(venda);
	}


}
