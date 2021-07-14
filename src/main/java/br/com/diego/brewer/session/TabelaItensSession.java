package br.com.diego.brewer.session;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.ItemVenda;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionScope
@Component
public class TabelaItensSession {

    private Set<TabelaItensVenda> tabelas = new HashSet<>();


    public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
        TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
        tabela.adicionarItem(cerveja, quantidade);
        tabelas.add(tabela);
    }

    public void alterarQuantidadeItens(String uuid, Cerveja cerveja, Integer quantidade) {
        TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
        tabela.alterarQuantidadeItens(cerveja, quantidade);
    }

    public void excluirItem(String uuid, Cerveja cerveja) {
        TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
        tabela.excluirItem(cerveja);
    }

    public List<ItemVenda> getItens(String uuid) {
        return buscarTabelaPorUuid(uuid).getItens();
    }

    public BigDecimal getValorTotal(String uuid) {
        return buscarTabelaPorUuid(uuid).getValorTotal();
    }

    private TabelaItensVenda buscarTabelaPorUuid(String uuid) {
        return tabelas.stream().filter(t -> t.getUuid().equals(uuid)).findAny().orElse(new TabelaItensVenda(uuid));
    }
}
