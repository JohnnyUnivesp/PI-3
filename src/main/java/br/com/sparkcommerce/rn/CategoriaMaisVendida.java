package br.com.sparkcommerce.rn;

import java.math.BigDecimal;

public class CategoriaMaisVendida {
	private String nome;
    private Double total;

    public CategoriaMaisVendida() {
    }

    public CategoriaMaisVendida(String nome, Double total) {
        this.nome = nome;
        this.total = total;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CategoriaMaisVendida{" +
                "nome='" + nome + '\'' +
                ", total=" + total +
                '}';
    }
    
}
