package br.com.diego.brewer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Informe um nome, campo obrigatório.")
	private String nome;

	@NotNull(message = "Informe o Estado, campo obrigatório.")
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_estado")
	private Estado estado;
	
	public Cidade() {
	
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setId(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cidade cidade = (Cidade) o;
		return Objects.equals(codigo, cidade.codigo);
	}

	@Override
	public int hashCode(){
		return Objects.hash(codigo);
	}
}
