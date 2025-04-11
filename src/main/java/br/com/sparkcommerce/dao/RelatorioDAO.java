package br.com.sparkcommerce.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.sparkcommerce.model.ItemVenda;
import br.com.sparkcommerce.model.Produto;
import br.com.sparkcommerce.model.Venda;

public class RelatorioDAO {

    @Inject
    private EntityManager em;

    public double calcularTotalVendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<Venda> vendas = em.createQuery(
            "SELECT v FROM Venda v WHERE v.dataPagamento BETWEEN :inicio AND :fim", Venda.class)
            .setParameter("inicio", inicio)
            .setParameter("fim", fim)
            .getResultList();

        double total = 0.0;

        for (Venda venda : vendas) {
            for (ItemVenda item : venda.getItens()) {
                Produto produto = em.find(Produto.class, item.getProdutoId().intValue());
                if (produto != null) {
                    total += produto.getValor() * item.getQuantidade();
                }
            }
        }

        return total;
    }

    public List<Venda> listarVendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return em.createQuery(
            "SELECT v FROM Venda v WHERE v.dataPagamento BETWEEN :inicio AND :fim ORDER BY v.dataPagamento", Venda.class)
            .setParameter("inicio", inicio)
            .setParameter("fim", fim)
            .getResultList();
    }
}
