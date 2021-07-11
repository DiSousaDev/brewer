package br.com.diego.brewer.service;

import java.util.List;

import br.com.diego.brewer.dto.CervejaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.diego.brewer.controller.filter.CervejaFilter;
import br.com.diego.brewer.model.Cerveja;

public interface CervejaService {

	Cerveja salvar(Cerveja cerveja);
	
	Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);
	
	List<Cerveja> buscarTodas();

	List<CervejaDTO> buscarPorSkuOuNome(String skuOuNome);

    Cerveja buscarPorCodigo(Long codigoCerveja);
}
