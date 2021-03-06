package br.com.diego.brewer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.diego.brewer.model.validation.AtributoConfirmacao;
import org.hibernate.annotations.DynamicUpdate;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message="Senhas não conferem.")
@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "Informe o nome, campo obrigatório.")
    private String nome;

    @NotBlank(message = "Informe o e-mail, campo obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    private String senha;

    @Transient
    private String confirmacaoSenha;

    private Boolean ativo;

    @Column(name = "data_nascimento", columnDefinition = "DATE")
    private LocalDate dataNascimento;

    @Size(min = 1, message = "Selecione ao menos 1 grupo.")
    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
    private List<Grupo> grupos;

    @PreUpdate
    private void preUpdate() {
        this.confirmacaoSenha = senha;
    }

    public Long getCodigo(){
        return codigo;
    }

    public void setCodigo(Long codigo){
        this.codigo = codigo;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getConfirmacaoSenha(){
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha){
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataNascimento(){
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;
    }

    public List<Grupo> getGrupos(){
        return grupos;
    }
    
    public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
    
    public boolean isNovo() {
    	return codigo == null;
    }

    public String verificaStatus() {
        return ativo ? "Ativo" : "Inativo";
    }
    
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(codigo, usuario.codigo);
    }

    @Override
    public int hashCode(){
        return Objects.hash(codigo);
    }
}
