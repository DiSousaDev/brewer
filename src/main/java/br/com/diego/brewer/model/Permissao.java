package br.com.diego.brewer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permissao")
public class Permissao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String nome;

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

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Permissao permissao = (Permissao) o;
        return Objects.equals(codigo, permissao.codigo);
    }

    @Override
    public int hashCode(){
        return Objects.hash(codigo);
    }
}
