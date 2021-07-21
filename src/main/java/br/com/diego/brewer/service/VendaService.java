package br.com.diego.brewer.service;

import br.com.diego.brewer.controller.filter.VendaFilter;
import br.com.diego.brewer.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendaService {

	Venda salvar(Venda venda);

    void emitir(Venda venda);

    Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);

    Venda buscarComItens(Long codigo);
}
