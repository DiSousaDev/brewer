package br.com.diego.brewer.session;


import br.com.diego.brewer.model.Cerveja;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;

public class TabelaItensVendaTest {

    private TabelaItensVenda tabelaItensVenda;

    @Before
    public void setUp() {
        this.tabelaItensVenda = new TabelaItensVenda();
    }

    @Test
    public void deveCalcularValorTotalSemItens() {
        Assert.assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComUmItem() {
        Cerveja cerveja = new Cerveja();
        BigDecimal valor = new BigDecimal("8.90");
        cerveja.setValor(valor);
        tabelaItensVenda.adicionarItem(cerveja, 1);
        Assert.assertEquals(valor, tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComVariosItens(){
        Cerveja cerveja1 = new Cerveja();
        cerveja1.setCodigo(1L);
        cerveja1.setValor(new BigDecimal("8.00"));
        Cerveja cerveja2 = new Cerveja();
        cerveja2.setCodigo(2L);
        cerveja2.setValor(new BigDecimal("5.99"));
        tabelaItensVenda.adicionarItem(cerveja1, 2);
        tabelaItensVenda.adicionarItem(cerveja2, 3);
        Assert.assertEquals(new BigDecimal("33.97"), tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveManterTamanhoListaMesmasCervejas(){

        Cerveja cerveja1 = new Cerveja();
        cerveja1.setCodigo(1L);
        Cerveja cerveja2 = new Cerveja();
        cerveja1.setCodigo(2L);
        tabelaItensVenda.adicionarItem(cerveja1, 2);
        tabelaItensVenda.adicionarItem(cerveja1, 2);
        tabelaItensVenda.adicionarItem(cerveja2, 1);
        Assert.assertEquals(2, tabelaItensVenda.total());

    }

    @Test
    public void deveAlterarQuantidadeDoItem(){

        Cerveja cerveja1 = new Cerveja();
        cerveja1.setCodigo(1L);
        cerveja1.setValor(new BigDecimal("8.00"));
        tabelaItensVenda.adicionarItem(cerveja1, 1);
        tabelaItensVenda.alterarQuantidadeItens(cerveja1, 3);

        Assert.assertEquals(new BigDecimal("24.00"), tabelaItensVenda.getValorTotal());

    }

    @Test
    public void deveExcluirItem(){
        Cerveja cerveja1 = new Cerveja();
        cerveja1.setCodigo(1L);
        cerveja1.setValor(new BigDecimal("8.00"));
        Cerveja cerveja2 = new Cerveja();
        cerveja2.setCodigo(2L);
        cerveja2.setValor(new BigDecimal("5.99"));
        tabelaItensVenda.adicionarItem(cerveja1, 2);
        tabelaItensVenda.adicionarItem(cerveja2, 3);

        tabelaItensVenda.excluirItem(cerveja2);

        Assert.assertEquals(1, tabelaItensVenda.total());
        Assert.assertEquals(new BigDecimal("16.00"), tabelaItensVenda.getValorTotal());
    }
}
