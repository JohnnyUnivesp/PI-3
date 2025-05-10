package br.com.sparkcommerce.model;

import java.util.Date;
import javax.persistence.*;

import br.com.olimposistema.aipa.model.Model;

@Entity
public class MovimentacaoEstoque extends Model {
    
    @ManyToOne
    private Produto produto;
    
    private Date dataMovimentacao;
    private int quantidade;
    private String tipo;
    private String motivo;
    
    @ManyToOne
    private Usuario usuario;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}

	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
    // Getters e Setters
    
}