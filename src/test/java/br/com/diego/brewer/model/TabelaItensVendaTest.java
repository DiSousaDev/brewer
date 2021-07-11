package br.com.diego.brewer.model;


import br.com.diego.brewer.session.TabelaItensVenda;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TabelaItensVendaTest {

    private TabelaItensVenda tabelaItensVenda;

    @Before
    public void setUp() {
        this.tabelaItensVenda = new TabelaItensVenda();
    }

    @Test
    public void deveCalcularValorTotalSemItens() {
        assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComUmItem() {
        Cerveja cerveja = new Cerveja();
        BigDecimal valor = new BigDecimal("8.90");
        cerveja.setValor(valor);
        tabelaItensVenda.adicionarItem(cerveja, 1);
        assertEquals(valor, tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComVariosItens(){
        Cerveja cerveja1 = new Cerveja();
        cerveja1.setValor(new BigDecimal("8.00"));
        Cerveja cerveja2 = new Cerveja();
        cerveja2.setValor(new BigDecimal("5.99"));
        tabelaItensVenda.adicionarItem(cerveja1, 2);
        tabelaItensVenda.adicionarItem(cerveja2, 3);
        assertEquals(new BigDecimal("33.97"), tabelaItensVenda.getValorTotal());
    }
}
