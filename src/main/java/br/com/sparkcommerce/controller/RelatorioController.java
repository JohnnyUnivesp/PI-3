package br.com.sparkcommerce.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.inject.Inject;
import br.com.caelum.vraptor.*;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.dao.RelatorioDAO;
import br.com.sparkcommerce.dao.VendaDAO;
import br.com.sparkcommerce.interceptors.SomenteAdmin;
import br.com.sparkcommerce.model.ItemVenda;
import br.com.sparkcommerce.model.Produto;
import br.com.sparkcommerce.model.RelatorioResumo;
import br.com.sparkcommerce.model.Venda;

@Controller
public class RelatorioController {

    @Inject private Result result;
    @Inject private RelatorioDAO relatorioDAO;
    @Inject private VendaDAO vendaDAO;
    @Inject private ProdutoDAO produtoDAO;
    
    @Get("relatorio") @SomenteAdmin
    public void formulario() {
    }

    @Post("relatorio")
	    public void gerar(String dataInicio, String dataFim) {
	        LocalDate inicio = LocalDate.parse(dataInicio);
	        LocalDate fim = LocalDate.parse(dataFim);
	    	List<Venda> vendas = vendaDAO.buscarPorData(inicio.atStartOfDay(), fim.atTime(23, 59, 59));

        double valorTotalSoma = 0;
        Map<String, Integer> rankingProdutos = new HashMap<>();
        Map<String, Double> vendasPorCategoria = new HashMap<>();

        for (Venda venda : vendas) {
            for (ItemVenda item : venda.getItens()) {
                Produto produto = produtoDAO.selectPorIdLong(item.getProdutoId());
                if (produto == null) continue;

                String nomeProduto = produto.getNome();
                String nomeCategoria = produto.getCategoria().getNome();

                int quantidade = item.getQuantidade();
                double subtotal = item.getValorUnitario() * quantidade;

                valorTotalSoma += subtotal;
                 if (rankingProdutos.containsKey(nomeProduto)) {
                rankingProdutos.put(nomeProduto, rankingProdutos.get(nomeProduto) + quantidade);
            } else {
                rankingProdutos.put(nomeProduto, quantidade);
            }

            if (vendasPorCategoria.containsKey(nomeCategoria)) {
                vendasPorCategoria.put(nomeCategoria, vendasPorCategoria.get(nomeCategoria) + subtotal);
            } else {
                vendasPorCategoria.put(nomeCategoria, subtotal);
            }
            }
        }
        
     // Ordenar produtos por quantidade vendida (decrescente)
        List<Map.Entry<String, Integer>> listaProdutos = new ArrayList<>(rankingProdutos.entrySet());
        Collections.sort(listaProdutos, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        Map<String, Integer> rankingOrdenado = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : listaProdutos) {
            rankingOrdenado.put(entry.getKey(), entry.getValue());
        }

        // Ordenar categorias por valor vendido (decrescente)
        List<Map.Entry<String, Double>> listaCategorias = new ArrayList<>(vendasPorCategoria.entrySet());
        Collections.sort(listaCategorias, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        Map<String, Double> categoriasOrdenadas = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : listaCategorias) {
            categoriasOrdenadas.put(entry.getKey(), entry.getValue());
        }

        BigDecimal valorTotal = BigDecimal.valueOf(valorTotalSoma).setScale(2, RoundingMode.HALF_UP);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorFormatado = nf.format(valorTotal);
        double ticketMedio = vendas.size() > 0 ? valorTotalSoma / vendas.size() : 0;

        RelatorioResumo relatorio = new RelatorioResumo(valorTotal, ticketMedio, rankingOrdenado, categoriasOrdenadas);

        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String inicioFormatado = inicio.format(formatter);
        String fimFormatado = fim.format(formatter);
        
        result.include("valorFormatado", valorFormatado);
        result.include("totalPedidos", vendas.size());
        result.include("relatorio", relatorio);
        result.include("inicio", inicio);
        result.include("fim", fim);
        result.include("inicioFormatado", inicioFormatado);
        result.include("fimFormatado", fimFormatado);
        
        result.of(this).formulario();
    }
}
