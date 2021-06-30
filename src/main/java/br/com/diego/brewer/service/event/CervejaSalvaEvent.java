package br.com.diego.brewer.service.event;

import org.springframework.util.ObjectUtils;

import br.com.diego.brewer.model.Cerveja;

public class CervejaSalvaEvent {
	
	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}
	
	public boolean temFoto() {
		return !ObjectUtils.isEmpty(cerveja.getFoto());
	}
	
}