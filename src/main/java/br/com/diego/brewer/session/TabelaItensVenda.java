package br.com.diego.brewer.session;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.ItemVenda;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SessionScope
@Component
public class TabelaItensVenda implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ItemVenda> itens = new ArrayList<>();

    public BigDecimal getValorTotal() {
        return itens.stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public void adicionarItem(Cerveja cerveja, Integer quantidade) {
        ItemVenda item = new ItemVenda();
        item.setCerveja(cerveja);
        item.setQuantidade(quantidade);
        item.setValorUnitario(cerveja.getValor());

        itens.add(item);

    }

    public int total() {
        return itens.size();
    }

    public List<ItemVenda> getItens(){
        return itens;
    }
}
