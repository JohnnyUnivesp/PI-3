package br.com.sparkcommerce.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import br.com.sparkcommerce.rn.CategoriaMaisVendida;
import br.com.sparkcommerce.rn.ProdutoMaisVendido;

public class RelatorioResumo {

	private BigDecimal valorTotal;
	private Double ticketMedio;
	private Map<String, Integer> rankingProdutos;
	private Map<String, Double> vendasPorCategoria;

	public RelatorioResumo(BigDecimal valorTotal, Double ticketMedio, 
                           Map<String, Integer> rankingProdutos, 
                           Map<String, Double> vendasPorCategoria) {
		this.valorTotal = valorTotal;
		this.ticketMedio = ticketMedio;
		this.rankingProdutos = rankingProdutos;
		this.vendasPorCategoria = vendasPorCategoria;
	}

	// Getters e Setters
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getTicketMedio() {
		return ticketMedio;
	}

	public void setTicketMedio(Double ticketMedio) {
		this.ticketMedio = ticketMedio;
	}

	public Map<String, Integer> getRankingProdutos() {
		return rankingProdutos;
	}

	public void setRankingProdutos(Map<String, Integer> rankingProdutos) {
		this.rankingProdutos = rankingProdutos;
	}

	public Map<String, Double> getVendasPorCategoria() {
		return vendasPorCategoria;
	}

	public void setVendasPorCategoria(Map<String, Double> vendasPorCategoria) {
		this.vendasPorCategoria = vendasPorCategoria;
	}
}
