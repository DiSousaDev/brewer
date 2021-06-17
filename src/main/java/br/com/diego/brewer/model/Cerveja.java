package br.com.diego.brewer.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Cerveja {
	
	@NotBlank(message = "Informe o SKU, campo obrigatório.")
	private String sku;
	@NotBlank(message = "Informe o nome, campo obrigatório.")
	private String nome;
	@Size(min = 1, max = 50, message = "O tamanho deve estar entre {min} e {max}.")
	private String descricao;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
