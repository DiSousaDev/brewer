package br.com.diego.brewer.session;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.ItemVenda;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

class TabelaItensVenda implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private List<ItemVenda> itens = new ArrayList<>();

    public TabelaItensVenda(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getValorTotal() {
        return itens.stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public void adicionarItem(Cerveja cerveja, Integer quantidade) {
        Optional<ItemVenda> itemVendaOptional = buscarItemPorCerveja(cerveja);

        ItemVenda itemVenda = null;
        if(itemVendaOptional.isPresent()) {
            itemVenda = itemVendaOptional.get();
            itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
        } else {
            itemVenda = new ItemVenda();
            itemVenda.setCerveja(cerveja);
            itemVenda.setQuantidade(quantidade);
            itemVenda.setValorUnitario(cerveja.getValor());
            itens.add(0, itemVenda);
        }
    }

    public void alterarQuantidadeItens(Cerveja cerveja, Integer quantidade) {
        ItemVenda itemVenda = buscarItemPorCerveja(cerveja).get();
        itemVenda.setQuantidade(quantidade);
    }

    public void excluirItem(Cerveja cerveja){
        int indice = IntStream.range(0, itens.size()).filter(i -> itens.get(i).getCerveja().equals(cerveja)).findAny().getAsInt();
        itens.remove(indice);
    }

    public int total() {
        return itens.size();
    }

    public List<ItemVenda> getItens(){
        return itens;
    }

    private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja){
        return itens.stream().filter(i -> i.getCerveja().equals(cerveja)).findAny();
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TabelaItensVenda that = (TabelaItensVenda) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
