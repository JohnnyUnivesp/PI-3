package br.com.sparkcommerce.rn;

public class ProdutoMaisVendido {
	private String nome;
    private int quantidade;

    public ProdutoMaisVendido() {
    }

    public ProdutoMaisVendido(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ProdutoMaisVendido{" +
                "nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
    
}
